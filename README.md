https://github.com/foamyguy/stacksites  
上記のプログラムを参考にし、改造しました。    

変更点

・デフォルトではすべてのタグを表示してしまうため、itemタグのみparseするように変更  
・CDATAセクションからJsoupを使って画像urlを取得しlistViewで表示  
・listViewで表示される商品画像を大きく変更  
・正規表現でm.ebayをwww.ebayに変更(m.ebayではxmlpullparserがエラーをはくため)  
・ユーザーからの入力を受け付けるInputActivityを追加  
　intentで入力されたurlをMainActivityに送り、Downloaderでrssを取得

まだ未完成ですがオプションメニューからebayAPIを使った方法も実装しています。    

追加したい機能  
・csv出力  
・オークションタイプや現在価格でのソート
　

