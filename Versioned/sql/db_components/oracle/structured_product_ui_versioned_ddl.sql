-- Primary Tables

create table sbl_renderer (
 	asset_version   		number(19)  		not null,
  	workspace_id    		varchar2(40)      	not null,
  	branch_id             		varchar2(40)  	not null,
  	is_head                  	number(1,0)    	not null,
  	version_deleted         	number(1,0)          not null,
  	version_editable       		number(1,0)           not null,
  	pred_version              	number(19)           null,
  	checkin_date              	timestamp               null,
	renderer_id 			varchar2(254)	not null,
	jsp_path 			varchar2(254)	null,
	application_context 		varchar2(254)	null,
	html_attributes 		varchar2(4000)	null,
	PRIMARY KEY(renderer_id)
);

create table sbl_promotion_ui (
 	asset_version            	number(19)                      not null,
  	workspace_id         		varchar2(40)            not null,
  	branch_id              		varchar2(40)            not null,
  	is_head                 	number(1,0)                     not null,
  	version_deleted           	number(1,0)                     not null,
  	version_editable          	number(1,0)                     not null,
  	pred_version                 	number(19)                      null,
  	checkin_date             	timestamp               null,
	promotion_ui_id			varchar2(254)	not null,
	promotion_id     		varchar2(254)  null,
	promotion_ui_name 		varchar2(254)	null,
	controller 			varchar2(254)	null references sbl_renderer(renderer_id),
	css_path 			varchar2(254)	null,
	PRIMARY KEY(promotion_ui_id)
);

create table sbl_product_ui (
 	asset_version             	number(19)                      not null,
  	workspace_id              	varchar2(40)            not null,
  	branch_id              		varchar2(40)            not null,
  	is_head                 	number(1,0)                     not null,
  	version_deleted           	number(1,0)                     not null,
  	version_editable          	number(1,0)                     not null,
  	pred_version              	number(19)                      null,
  	checkin_date              	timestamp               null,
        product_ui_id           	varchar2(254)   not null,
        product_id              	varchar2(254)   null,
        product_name            	varchar2(254)   null,
        promotion_ui_id         	varchar2(254)   null references sbl_promotion_ui (promotion_ui_id),
        root_product_flag       	number(1,0)     null,
        css_path                	varchar2(254)   null,
        CHECK (root_product_flag IN (0, 1)),
        PRIMARY KEY(product_ui_id)
);

create table sbl_block (
 	asset_version             	number(19)                      not null,
  	workspace_id              	varchar2(40)            not null,
  	branch_id               	varchar2(40)            not null,
  	is_head                     	number(1,0)                     not null,
  	version_deleted           	number(1,0)                     not null,
  	version_editable          	number(1,0)                     not null,
  	pred_version              	number(19)                      null,
  	checkin_date              	timestamp               null,
        block_id                	varchar2(254)   not null,
        name                    	varchar2(254)   null,
        display_name            	varchar2(254)   null,
        type                    	integer null,
        renderer                	varchar2(254)   null references sbl_renderer(renderer_id),
        active_flag             	number(1,0)     null,
        html_attributes         	varchar2(4000)   null,
        CHECK (active_flag IN (0, 1)),
        PRIMARY KEY(block_id)
);

--Auxilary Tables

create table sbl_attribute_block (
	asset_version             	number(19)                      not null,
        block_id                	varchar2(254)   not null references sbl_block(block_id),
        attribute_id            	varchar2(254)   null,
        attribute_name          	varchar2(254)   null,
        PRIMARY KEY(block_id)
);

create table sbl_relationship_block (
	asset_version             	number(19)                      not null,
        block_id                	varchar2(254)   not null references sbl_block(block_id),
        relationship_id         	varchar2(254)   null,
        PRIMARY KEY(block_id)
);

-- Multi Tables

create table sbl_promotion_ui_roots (
	asset_version             	number(19)                      not null,
	promotion_ui_id			varchar2(254)	not null references sbl_promotion_ui (promotion_ui_id),
	sequence_num 			integer	not null,
	product_id 			varchar2(254)	null,
	PRIMARY KEY(promotion_ui_id, sequence_num)
);

create table sbl_product_blocks (
	asset_version            	number(19)                      not null,
	product_ui_id			varchar2(254)	not null references sbl_product_ui (product_ui_id),
	sequence_num 			integer	not null,
	block_id 			varchar2(254)	null references sbl_block(block_id),
	PRIMARY KEY(product_ui_id, sequence_num)
);

create table sbl_child_product_ui (
	asset_version             	number(19)                      not null,
	block_id			varchar2(254)	not null references sbl_block(block_id),
	sequence_num 			integer	not null,
	product_ui_id 			varchar2(254)	null references sbl_product_ui(product_ui_id),
	PRIMARY KEY(block_id, sequence_num)
);
