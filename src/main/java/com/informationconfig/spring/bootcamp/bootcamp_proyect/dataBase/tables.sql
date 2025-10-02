

create table academyTutor (
    academyTutor_id varchar(50) not null,
    name varchar(50) not null,
    email varchar(80) not null,
    password varchar(20) not null,
    academy varchar(100) not null,
    department varchar(100) null,

    constraint pk_academyTutor primary key (academyTutor_id)
);

create table companyTutor (
    companyTutor_id varchar(50) not null,
    name varchar(50) not null,
    email varchar(80) not null,
    password varchar(20) not null,
    company varchar(100) not null,
    position varchar(100) null,

    constraint pk_companyTutor primary key (companyTutor_id)
);

create table performanceReport (
    performanceReport_id varchar(50) not null,
    reportDate date not null,
    content varchar(1000) null,
    academyTutor_id varchar(50) not null,
    companyTutor_id varchar(50) not null,

    constraint pk_performanceReport primary key (performanceReport_id),
    constraint fk_academyTutor foreign key (academyTutor_id) references academyTutor(academyTutor_id),
    constraint fk_companyTutor foreign key (companyTutor_id) references companyTutor(companyTutor_id)
);

create table team (
    team_id varchar(50) not null,
    name varchar(50) not null,
    description varchar(255) null,
    academyTutor_id varchar(50) not null,
    companyTutor_id varchar(50) not null,

    constraint pk_team primary key (team_id),
    constraint fk_team_academyTutor foreign key (academyTutor_id) references academyTutor(academyTutor_id),
    constraint fk_team_companyTutor foreign key (companyTutor_id) references companyTutor(companyTutor_id)
);

create table board (
    board_id varchar(50) not null,
    name varchar(50) not null,
    description varchar(255) null,
    date_created date not null,
    team_id varchar(50) not null,
    
    constraint pk_board primary key (board_id),
    constraint fk_team foreign key (team_id) references team(team_id)
);

create table list (
    list_id varchar(50) not null,
    name varchar(50) not null,
    description varchar(1000) null,
    board_id varchar(50) not null,

    constraint pk_list primary key (list_id),
    constraint fk_list_board foreign key (board_id) references board(board_id)
);

create table task (
    task_id varchar(50) not null,
    title varchar(50) not null,
    description varchar(255) null,
    status varchar(50) not null,
    due_date date null,
    list_id varchar(50) not null,

    constraint pk_task primary key (task_id),
    constraint fk_task_list foreign key (list_id) references list(list_id)
);

create table intern (
    intern_id varchar(50) not null,
    name varchar(50) not null,
    email varchar(50) not null,
    password varchar(20) not null,
    university varchar(100) not null,
    academyTutor_id varchar(50) not null,
    companyTutor_id varchar(50) not null,
    task_id varchar(50) not null,
    team_id varchar(50) not null,

    constraint pk_intern primary key (intern_id),
    constraint fk_intern_academyTutor foreign key (academyTutor_id) references academyTutor(academyTutor_id),
    constraint fk_intern_companyTutor foreign key (companyTutor_id) references companyTutor(companyTutor_id),
    constraint fk_intern_task foreign key (task_id) references task(task_id),
    constraint fk_intern_team foreign key (team_id) references team(team_id)
)

create table logBook (
    logBook_id varchar(50) not null,
    entryDate date not null,
    content varchar(255) null,
    evidences varchar(255) null,
    intern_id varchar(50) not null,
    
    constraint pk_logBook primary key (logBook_id),
    constraint fk_logBook_intern foreign key (intern_id) references intern(intern_id)
);

