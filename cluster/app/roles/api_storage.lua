local cartridge = require('cartridge')

local function init_schema()
    local frame = box.schema.space.create('frame', { if_not_exists=true })
    frame:format({
        {name="y", type="unsigned"},
        {name="x", type="unsigned"},
        {name="sym", type="string"},
        {name="bucket_id", type="unsigned"},
    })
    frame:create_index('primary', { parts={{field='y'}, {field='x'}},
                                    if_not_exists=true })
    frame:create_index('bucket_id', { parts={{field="bucket_id"}},
                                      unique=false,
                                      if_not_exists=true })

end

local function init(opts) -- luacheck: no unused args
     if opts.is_master then
         init_schema()
     end

    return true
end

local function stop()
end

local function validate_config(conf_new, conf_old) -- luacheck: no unused args
    return true
end

local function apply_config(conf, opts) -- luacheck: no unused args
    -- if opts.is_master then
    -- end

    return true
end

return {
    role_name = 'app.roles.api_storage',
    init = init,
    stop = stop,
    validate_config = validate_config,
    apply_config = apply_config,
    dependencies = {'cartridge.roles.crud-storage'},
}
