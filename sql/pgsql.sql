------------------------------------------
-- 1. 创建主数据库
------------------------------------------
-- 如果数据库已存在，先断开所有连接再删除
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'springcloud' AND pid <> pg_backend_pid();

--DROP DATABASE IF EXISTS springcloud;
--CREATE DATABASE springcloud
--    ENCODING 'UTF8'
--    LC_COLLATE 'en_US.UTF-8'
--    LC_CTYPE 'en_US.UTF-8'
--    TEMPLATE template0;

-- 切换到新创建的数据库

------------------------------------------
-- 2. 创建扩展（如果需要）
------------------------------------------
-- 如果需要 UUID 或其他扩展
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

------------------------------------------
-- 3. 为每个微服务创建模式
------------------------------------------
CREATE SCHEMA IF NOT EXISTS integrated_storage;
CREATE SCHEMA IF NOT EXISTS integrated_account;
CREATE SCHEMA IF NOT EXISTS integrated_order;
CREATE SCHEMA IF NOT EXISTS integrated_praise;

------------------------------------------
-- 4. 创建 Storage 库存微服务的表和初始数据
------------------------------------------
SET search_path TO integrated_storage, public;

-- 创建 storage 表
CREATE TABLE storage (
    id BIGSERIAL PRIMARY KEY,
    commodity_code VARCHAR(255),
    count INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建唯一索引
CREATE UNIQUE INDEX idx_storage_commodity_code ON storage(commodity_code);

-- 插入初始数据
INSERT INTO storage (commodity_code, count, create_time, update_time)
VALUES ('1', 100, '2022-08-07 22:48:29', '2022-08-14 13:49:05');

-- 创建 Seata undo_log 表
CREATE TABLE undo_log (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    xid VARCHAR(100) NOT NULL,
    context VARCHAR(128) NOT NULL,
    rollback_info BYTEA NOT NULL,
    log_status INTEGER NOT NULL,
    log_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    log_modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ux_undo_log UNIQUE (xid, branch_id)
);

------------------------------------------
-- 5. 创建 Account 账户微服务的表和初始数据
------------------------------------------
SET search_path TO integrated_account, public;

-- 创建 account 表
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255),
    money INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入初始数据
INSERT INTO account (user_id, money, create_time, update_time)
VALUES ('admin', 3, '2022-08-07 22:53:01', '2022-08-14 13:49:05');

-- 创建 Seata undo_log 表
CREATE TABLE undo_log (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    xid VARCHAR(100) NOT NULL,
    context VARCHAR(128) NOT NULL,
    rollback_info BYTEA NOT NULL,
    log_status INTEGER NOT NULL,
    log_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    log_modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ux_undo_log UNIQUE (xid, branch_id)
);

------------------------------------------
-- 6. 创建 Order 订单微服务的表
------------------------------------------
SET search_path TO integrated_order, public;

-- 创建 orders 表（避免使用关键字）
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255),
    commodity_code VARCHAR(255),
    count INTEGER,
    money INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建 Seata undo_log 表
CREATE TABLE undo_log (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    xid VARCHAR(100) NOT NULL,
    context VARCHAR(128) NOT NULL,
    rollback_info BYTEA NOT NULL,
    log_status INTEGER NOT NULL,
    log_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    log_modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ux_undo_log UNIQUE (xid, branch_id)
);

------------------------------------------
-- 7. 创建 Praise 点赞业务的表和初始数据
------------------------------------------
SET search_path TO integrated_praise, public;

-- 创建 item 表
CREATE TABLE item (
    id BIGSERIAL PRIMARY KEY,
    praise INTEGER,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入初始数据
INSERT INTO item (praise, create_time, update_time)
VALUES (0, '2022-08-14 00:33:50', '2022-08-14 14:07:34');

------------------------------------------
-- 8. 重置搜索路径到默认
------------------------------------------
SET search_path TO public;

------------------------------------------
-- 9. 查看所有创建的模式和表
------------------------------------------
-- 查看所有模式
SELECT schema_name FROM information_schema.schemata
WHERE schema_name LIKE 'integrated_%'
ORDER BY schema_name;

-- 查看每个模式下的表
SELECT
    table_schema as schema_name,
    table_name,
    table_type
FROM information_schema.tables
WHERE table_schema LIKE 'integrated_%'
ORDER BY table_schema, table_name;

------------------------------------------
-- 10. 为每个微服务创建专用用户（可选）
------------------------------------------
-- 创建只读用户（用于监控等）
CREATE USER springcloud_ro WITH PASSWORD 'readonly_pass';
GRANT CONNECT ON DATABASE springcloud TO springcloud_ro;
GRANT USAGE ON SCHEMA public TO springcloud_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA integrated_storage TO springcloud_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA integrated_account TO springcloud_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA integrated_order TO springcloud_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA integrated_praise TO springcloud_ro;

-- 为每个微服务创建独立用户
DO $$
BEGIN
    -- Storage 微服务用户
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'storage_user') THEN
        CREATE USER storage_user WITH PASSWORD 'storage_pass';
        GRANT CONNECT ON DATABASE springcloud TO storage_user;
        GRANT USAGE, CREATE ON SCHEMA integrated_storage TO storage_user;
        GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA integrated_storage TO storage_user;
        GRANT USAGE ON ALL SEQUENCES IN SCHEMA integrated_storage TO storage_user;
    END IF;

    -- Account 微服务用户
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'account_user') THEN
        CREATE USER account_user WITH PASSWORD 'account_pass';
        GRANT CONNECT ON DATABASE springcloud TO account_user;
        GRANT USAGE, CREATE ON SCHEMA integrated_account TO account_user;
        GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA integrated_account TO account_user;
        GRANT USAGE ON ALL SEQUENCES IN SCHEMA integrated_account TO account_user;
    END IF;

    -- Order 微服务用户
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'order_user') THEN
        CREATE USER order_user WITH PASSWORD 'order_pass';
        GRANT CONNECT ON DATABASE springcloud TO order_user;
        GRANT USAGE, CREATE ON SCHEMA integrated_order TO order_user;
        GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA integrated_order TO order_user;
        GRANT USAGE ON ALL SEQUENCES IN SCHEMA integrated_order TO order_user;
    END IF;

    -- Praise 微服务用户
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'praise_user') THEN
        CREATE USER praise_user WITH PASSWORD 'praise_pass';
        GRANT CONNECT ON DATABASE springcloud TO praise_user;
        GRANT USAGE, CREATE ON SCHEMA integrated_praise TO praise_user;
        GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA integrated_praise TO praise_user;
        GRANT USAGE ON ALL SEQUENCES IN SCHEMA integrated_praise TO praise_user;
    END IF;
END $$;