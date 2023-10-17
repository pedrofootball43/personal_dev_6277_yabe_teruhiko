-- ユーザーテーブルデータ
INSERT INTO users(login_id, name, password) VALUES('tana000', '田中太郎', 'himitu');
INSERT INTO users(login_id, name, password) VALUES('yamada88', '山田次郎', 'himitu');
INSERT INTO users(login_id, name, password) VALUES('sato36', '佐藤三郎', 'himitu');
-- カテゴリー
INSERT INTO categories(name) VALUES('趣味');
INSERT INTO categories(name) VALUES('家事');
INSERT INTO categories(name) VALUES('仕事');
-- ToDoリストデータ
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 2, 'たまねぎ', 'カレー用', '2023/10/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 3, '同僚に連絡', '週末の会議について', '2023/10/30');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 1, 'ホテル予約', 'ライブ遠征', '2023/12/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 2, '人参', 'カレー用', '2023/10/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 3, '給料もらう', '一番大事', '2023/9/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 2, 'ジャガイモ', 'カレー用', '2023/8/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(1, 1, 'サッカー試合観戦', 'FM', '2023/12/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(2, 2, 'シチュー', '今日の献立', '2023/10/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(3, 3, 'エクセル編集', '週末の会議用', '2023/10/15');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline) VALUES(3, 1, '弁当', '弁当屋に取りに行く', '2023/10/15');
