create database knk_football;
use knk_football;

create table users (
                       id int primary key not null auto_increment,
                       fname varchar(30),
                       pass varchar(25),
                       date_create timestamp,
                       date_modified timestamp,
                       role enum ('admin' , 'user' )
);

create table player(
                       id int primary key not null unique auto_increment,
                       name varchar(30) not null,
                       position varchar(30) not null,
                       number int not null,
                       birthday date,
                       image text
);

create table team(
                     id int primary key  not null unique auto_increment,
                     name varchar(30) not null,
                     stadium varchar(30) not null
);

create table squad (
                       id int primary key not null unique auto_increment,
                       team_id int,
                       foreign key (team_id) references team(id)
);

create table playersquad(
                            sid int,
                            pid int,
                            foreign key (sid) references squad(id),
                            foreign key (pid) references player(id)
);

create table matches(
                        id int primary key not null auto_increment,
                        hometeam_id int,
                        awayteam_id int,
                        hometeam_goals int,
                        awayteam_goals int,
                        match_date datetime,
                        foreign key (hometeam_id) references team(id),
                        foreign key (awayteam_id) references team(id)
);

create table league(
                       id int primary key not null unique auto_increment,
                       name varchar(30)

);

create table league_team(
                            league_id int,
                            team_id int,
                            year timestamp,
                            foreign key (league_id) references league(id),
                            foreign key (team_id) references team(id)
);
create table league_matches (
                                league_id int,
                                match_id int,
                                foreign key (league_id) references league(id),
                                foreign key (match_id) references matches(id) on delete cascade
);

create table season (
                        id int primary key not null unique auto_increment,
                        name varchar(10),
                        start date,
                        end date
);

create table goal(
                     id int primary key not null unique auto_increment,
                     game int,
                     team int,
                     scored int,
                     assisted int default null,
                     minute varchar(10),
                     owngoal boolean default false,
                     penalty boolean default false,
                     foreign key (team) references team(id),
                     foreign key (game) references matches(id),
                     foreign key (scored) references player(id),
                     foreign key (assisted) references player(id)
);
create table match_statistics(
                                 id int primary key not null unique auto_increment,
                                 match_id int,
                                 home_team_goals int,
                                 away_team_goals int,
                                 possession_home decimal(5,2),
                                 possession_away decimal(5,2),
                                 shots_home int,
                                 shots_away int,
                                 corners_home int,
                                 corners_away int,
                                 fouls_home int,
                                 fouls_away int,
                                 yellow_cards_home int,
                                 yellow_cards_away int,
                                 red_cards_home int,
                                 red_cards_away int,
                                 foreign key (match_id) references matches(id)
);

create table player_statistics (
                                   id int primary key not null unique auto_increment,
                                   player_id int,
                                   goals int,
                                   assists int,
                                   minutes_played int,
                                   yellow_cards int,
                                   red_cards int,
                                   foreign key (player_id) references player(id)
);

create table standings(
                          id int primary key not null unique auto_increment,
                          team_id int,
                          league_id int,
                          matches_played int,
                          wins int,
                          losses int,
                          goals_for int,
                          goals_against int,
                          points int,
                          foreign key (team_id) references Team(id),
                          foreign key (league_id) references League(id)
);
alter table league
    add column season_id int,
    add FOREIGN KEY (season_id) REFERENCES season(id);

alter table league
    add column league_logo text;

alter table team
    add column team_logo text;

INSERT INTO match_statistics (match_id, home_team_goals, away_team_goals, possession_home, possession_away, shots_home, shots_away, corners_home, corners_away, fouls_home, fouls_away, yellow_cards_home, yellow_cards_away, red_cards_home, red_cards_away)
VALUES (8, 2, 1, 0.6, 0.4, 10, 5, 8, 3, 12, 7, 2, 3, 0, 1);
ALTER TABLE users
    ADD COLUMN firstName VARCHAR(30),
    ADD COLUMN lastName VARCHAR(30),
    ADD COLUMN email VARCHAR(50),
    ADD COLUMN salt VARCHAR(255),
    ADD COLUMN passwordHash VARCHAR(255);


-- dropping the old columns
ALTER TABLE users
    DROP COLUMN fname,
    DROP COLUMN pass,
    DROP COLUMN date_create,
    DROP COLUMN date_modified;

USE knk_football;

-- Insert values into the league table
INSERT INTO league (name) VALUES ('Premier League');
INSERT INTO league (name) VALUES ('La Liga');
INSERT INTO league (name) VALUES ('Serie A');

-- Insert values into the team table
INSERT INTO team (name, stadium) VALUES ('Team A', 'Stadium A');
INSERT INTO team (name, stadium) VALUES ('Team B', 'Stadium B');
INSERT INTO team (name, stadium) VALUES ('Team C', 'Stadium C');

-- Insert values into the matches table
INSERT INTO matches (hometeam_id, awayteam_id, hometeam_goals, awayteam_goals, match_date)
VALUES (1, 2, 2, 1, '2023-05-20 15:00:00');
INSERT INTO matches (hometeam_id, awayteam_id, hometeam_goals, awayteam_goals, match_date)
VALUES (2, 3, 3, 0, '2023-06-22 18:00:00');
INSERT INTO matches (hometeam_id, awayteam_id, hometeam_goals, awayteam_goals, match_date)
VALUES (3, 1, 1, 1, '2023-07-12 20:00:00');

-- Insert values into the league_matches table
INSERT INTO league_matches (league_id, match_id) VALUES (1, 1);
INSERT INTO league_matches (league_id, match_id) VALUES (2, 2);
INSERT INTO league_matches (league_id, match_id) VALUES (3, 3);

ALTER TABLE player_statistics
    ADD CONSTRAINT fk_player
        FOREIGN KEY (player_id)
            REFERENCES player(id)
            ON DELETE SET NULL;