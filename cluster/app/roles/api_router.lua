local vshard = require('vshard')

-- function to get cluster schema
local function crud_get_schema()
    local replicaset = select(2, next(vshard.router.routeall()))
    local uniq_spaces = {}
    local spaces_ids = {}
    for _, space in pairs(replicaset.master.conn.space) do

        if (spaces_ids[space.id] == nil) then
            local space_copy = {
                engine = space.engine,
                field_count = space.field_count,
                id = space.id,
                name = space.name,
                indexes = {},
                format = space._format,
            }

            for i, space_index in pairs(space.index) do
                if type(i) == 'number' then
                    local index_copy = {
                        id = space_index.id,
                        name = space_index.name,
                        unique = space_index.unique,
                        type = space_index.type,
                        parts = space_index.parts,
                    }
                    table.insert(space_copy.indexes, index_copy)
                end
            end

            table.insert(uniq_spaces, { space_copy })
            spaces_ids[space.id] = true
        end
    end
    return uniq_spaces
end

function get_frames(from, to)
    return crud.select('frame', {
        { '>=', 'primary', from },
        { '<', 'primary', to }
    })
end

local function init(opts)
    -- some clutches for springdata
    rawset(_G, 'crud_get_schema', crud_get_schema)
    return true
end

local function stop()
end

local function validate_config(conf_new, conf_old)
    -- luacheck: no unused args
    return true
end

local function apply_config(conf, opts)
    -- luacheck: no unused args
    -- if opts.is_master then
    -- end

    return true
end

return {
    role_name = 'app.roles.api_router',
    init = init,
    stop = stop,
    validate_config = validate_config,
    apply_config = apply_config,
    dependencies = { 'cartridge.roles.crud-router' },
}
