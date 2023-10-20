-- ユーザーテーブルデータ
INSERT INTO users(login_id, name, password) VALUES('tana000', '田中太郎', 'himitu');
INSERT INTO users(login_id, name, password) VALUES('yamada88', '山田次郎', 'himitu');
INSERT INTO users(login_id, name, password) VALUES('sato36', '佐藤三郎', 'himitu');
-- カテゴリー
INSERT INTO categories(user_id,name) VALUES(1,'趣味');
INSERT INTO categories(user_id,name) VALUES(1,'家事');
INSERT INTO categories(user_id,name) VALUES(1,'仕事');
INSERT INTO categories(user_id,name) VALUES(1,'勉強');
INSERT INTO categories(user_id,name) VALUES(2,'趣味');
INSERT INTO categories(user_id,name) VALUES(2,'家事');
INSERT INTO categories(user_id,name) VALUES(2,'仕事');
INSERT INTO categories(user_id,name) VALUES(3,'趣味');
INSERT INTO categories(user_id,name) VALUES(3,'家事');
INSERT INTO categories(user_id,name) VALUES(3,'仕事');

-- ToDoリストデータ
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(1, 2, '買い物行く', 'タマネギ、ジャガイモ（種類大事）　カレー用', '2023/11/11', '未');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(1, 3, '同僚に連絡', '週末の会議について', '2023/10/30', '未');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(1, 1, 'ホテル予約', 'ライブ遠征', '2023/09/23', '未');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(1, 3, '日報の入力', '学習内容入力必須', '2023/12/12', '未');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(1, 1, '英単語', '目標5分以上', '2023/10/28','未'); 
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(1, 3, '引っ越し準備', '計画的に', '2023/10/15', '未');

INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation, finish_date) VALUES(1, 1, 'サッカー試合観戦', 'ユニフォーム必須', '2023/09/21', '済', '2023/09/21');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation, finish_date) VALUES(1, 1, 'ラーメン食べに行く', '必須', '2023/10/23', '済', '2023/10/20');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation, finish_date) VALUES(1, 1, '勉強', '頑張る', '2023/10/24', '済', '2023/10/23');

INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(2, 2, 'シチュー', '今日の献立', '2023/10/15', '未');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(3, 3, 'エクセル編集', '週末の会議用', '2023/10/15', '未');
INSERT INTO tasks(user_id, category_id, task, task_detail, deadline, situation) VALUES(3, 1, '弁当', '弁当屋に取りに行く', '2023/10/15', '未');
