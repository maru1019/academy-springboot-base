-- study_timeにデフォルト値を設定
ALTER TABLE learning_data
ALTER COLUMN study_time SET DEFAULT 0;

-- nameにデフォルト値を設定
ALTER TABLE learning_data
ALTER COLUMN name SET DEFAULT '';
