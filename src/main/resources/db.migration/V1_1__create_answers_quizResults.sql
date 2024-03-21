CREATE TABLE answers (
                         rowid BIGSERIAL PRIMARY KEY,
                         question_id BIGINT,
                         content VARCHAR(255),
                         value VARCHAR(255),
                         value_type VARCHAR(50),
                         FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

CREATE TABLE quiz_results (
                              rowid BIGSERIAL PRIMARY KEY,
                              quiz_id BIGINT,
                              user_id BIGINT,
                              value_type VARCHAR(255),
                              value VARCHAR(255),
                              attitude VARCHAR(255),
                              FOREIGN KEY (user_id) REFERENCES users(id)
);