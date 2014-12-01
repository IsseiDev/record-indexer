create table Project
(
	id integer not null primary key autoincrement,
	title varchar(255) not null,
	recordsperimage integer not null,
	firstycoord integer not null,
	recordheight integer not null
);

create table Batch
(
	id integer not null primary key autoincrement,
	filename varchar(255),
	isRecorded Boolean,
	currentIndexer integer,
	project_id integer,
	FOREIGN KEY(project_id) REFERENCES Project(id)
);

create table User
(
	id integer not null primary key autoincrement,
	firstname varchar(30),
	lastname varchar(30),
	username varchar(30) not null,
	password varchar(30) not null,
	indexedrecords integer not null,
	email varchar(30),
	current_batch integer,
	FOREIGN KEY(current_batch) REFERENCES Batch(id)
);


create table Field
(
	id integer not null primary key autoincrement,
	title varchar(255),
	xcoord integer not null,
	width integer not null,
	helphtml varchar(255) not null,
	knowndata varchar(255),
	position integer not null,
	project_id integer,
	FOREIGN KEY(project_id) REFERENCES Project(id)
);


create table Record
(
	id integer not null primary key autoincrement,
	batch_id integer,
	record_number integer,
	FOREIGN KEY(batch_id) REFERENCES Batch(id)
);

create table Value
(
	id integer not null primary key autoincrement,
	field_id integer,
	record_id integer,
	value varchar(255) not null,
	FOREIGN KEY(field_id) REFERENCES Field(id),
	FOREIGN KEY(record_id) REFERENCES Record(id)
);