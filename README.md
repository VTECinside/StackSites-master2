変更点

・デフォルトではすべてのタグを表示してしまうため、itemタグのみparseするように変更  
・CDATAセクションからJsoupを使って画像urlを取得するように変更  
・listViewで表示される商品画像を大きく変更  
・正規表現でm.ebayをwww.ebayに変更(m.ebayではxmlpullparserがエラーをはくため)  
・ユーザーからの入力を受け付けるInputActivityを追加  
　intentで入力されたurlをactivity間でやりとり  

