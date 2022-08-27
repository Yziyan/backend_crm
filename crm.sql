create table category
(
    id        smallint unsigned auto_increment comment '主键',
    name      varchar(20)       default '' not null comment '名称',
    parent_id smallint unsigned default 0  not null comment '父级种类',
    constraint category_id_uindex
        unique (id)
)
    comment '种类表';

alter table category
    add primary key (id);

create table department
(
    id        smallint unsigned auto_increment comment '主键',
    name      varchar(20)       default '' not null comment '名称',
    leader    varchar(20)       default '' not null comment '领导人',
    parent_id smallint unsigned default 0  not null comment '上级部门',
    constraint department_id_uindex
        unique (id)
)
    comment '部门表';

alter table department
    add primary key (id);

create table goods
(
    id           int unsigned auto_increment comment '主键',
    created_time datetime          default CURRENT_TIMESTAMP not null comment '创建时间',
    name         varchar(50)       default ''                not null comment '名称',
    old_price    decimal(10, 2)    default 0.00              not null comment '原价',
    new_price    decimal(10, 2)    default 0.00              not null comment '现价',
    description  varchar(50)       default ''                not null comment '描述信息',
    img_url      varchar(100)      default ''                not null comment '图片',
    state        tinyint unsigned  default 1                 not null comment '上架状态',
    stock        int unsigned      default 0                 not null comment '库存',
    sale_count   int unsigned      default 0                 not null comment '销量',
    favor_count  int unsigned      default 0                 not null comment '点赞数',
    address      varchar(20)       default ''                not null comment '生产地址',
    category_id  smallint unsigned default 0                 not null comment '种类ID',
    constraint goods_id_uindex
        unique (id)
)
    comment '商品表';

alter table goods
    add primary key (id);

create table resource
(
    id         smallint unsigned auto_increment comment '主键',
    name       varchar(20)       default '' not null comment '资源名称',
    uri        varchar(50)       default '' not null comment '链接地址',
    permission varchar(20)       default '' not null comment '权限标识',
    type       tinyint unsigned  default 0  not null comment '资源类型【0：目录、1：菜单、2：目录】',
    sn         smallint unsigned default 0  not null comment '序号',
    parent_id  smallint unsigned default 0  not null comment '父级ID',
    icon       varchar(20)       default '' not null comment '图标',
    constraint resource_id_uindex
        unique (id)
)
    comment '资源表';

alter table resource
    add primary key (id);

create table role
(
    id    smallint unsigned auto_increment comment '主键',
    name  varchar(11) default '' not null comment '角色名称',
    intro varchar(50) default '' not null comment '角色介绍',
    constraint role_id_uindex
        unique (id),
    constraint role_name_uindex
        unique (name)
)
    comment '角色表';

alter table role
    add primary key (id);

create table role_resource
(
    role_id     smallint unsigned default 0 not null comment '角色ID',
    resource_id smallint unsigned default 0 not null comment '资源ID',
    primary key (role_id, resource_id)
)
    comment '角色-资源';

create table user
(
    id            int auto_increment comment '主键',
    created_time  datetime          default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime          default CURRENT_TIMESTAMP not null comment '更新时间',
    name          varchar(20)       default ''                not null comment '用户名',
    password      varchar(32)       default ''                not null comment '密码',
    nickname      varchar(20)       default ''                not null comment '昵称',
    photo         varchar(100)      default ''                not null comment '头像',
    album         varchar(500)      default ''                not null comment '相册',
    cellphone     varchar(11)       default ''                not null comment '电话号码',
    enable        tinyint unsigned  default 1                 not null comment '账号状态【1：可用、0：禁用】',
    department_id smallint unsigned default 0                 not null comment '部门ID',
    constraint user_id_uindex
        unique (id),
    constraint user_name_uindex
        unique (name)
)
    comment '用户表';

alter table user
    add primary key (id);

create table user_role
(
    user_id int               default 0 not null comment '用户ID',
    role_id smallint unsigned default 0 not null comment '角色ID',
    primary key (user_id, role_id)
)
    comment '用户-角色';


