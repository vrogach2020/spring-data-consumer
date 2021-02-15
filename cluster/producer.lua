#!/usr/bin/env tarantool

local log = require('log')
local clock = require('clock')

local fiber = require('fiber')

local netbox = require('net.box')
local socket = require('socket')

local timeout = 20

--[[
    Подключаемся к источнику информации
]]
s = socket.tcp_connect('towel.blinkenlights.nl', 23, timeout)
if s == nil then
    log.info('Could not connect to ascii server')
    os.exit(1)
end

--[[
    По бинарному протоколу подключаемся к `Tarantool`
]]
conn = netbox.connect('admin:secret-cluster-cookie@127.0.0.1:3301')

local x = 0
local y = 0
while true do
    --[[
        Для простоты по одному байту читаем источник
    ]]
    local data = s:read(1, timeout)
    if data == nil or #data == 0 then
        --[[
            Источник закончился
        ]]
        break
    end

    --[[
        Если это перевод строки, увеличиваем координату `y`, обнуляем `x`
        Иначе увеличиваем `x`
    ]]
    if data == '\n' then
        y = y + 1
        x = 0
    else
        x = x + 1
    end

    --[[
        Бакеты нумеруются с единицы
    ]]
    local bucket_id = (y % 32) + 1

    --<< Упрощаем вставку объектов
    --<< Но всё ещё генерируем bucket_id
    local rc, err = conn:call('crud.replace_object', {
        'frame',
        {x=x, y=y, sym=data, bucket_id=bucket_id},
        {
            bucket_id=bucket_id
        }})

    if err ~= nil then
        log.info(err)
        fiber.sleep(1)
        -- os.exit(1)
    end

    io.write(data)
end

os.exit(1)
