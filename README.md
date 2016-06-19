![alt tag](http://s1.gazo.cc/up/196077.png)
![alt tag](http://s1.gazo.cc/up/196078.png)


https://github.com/foamyguy/stacksites  
上記のプログラムを参考にし、改造しました。    

使い方  
ebayで目的の商品を検索し、検索結果のurlをEditTextに貼り付けcreate RSSを押す

内外価格差を見つける手助けをし、データとして活用するという目的を達成するためのプログラムとして
作っています。

変更点

・ebayでrssを表示するには検索結果のurl末尾にリクエストパラメータ&_rss=1を追加する必要があるのでそれを自動で補完するよう変更  
・デフォルトではすべてのタグを表示してしまうため、itemタグのみparseするように変更  
・CDATAセクションからJsoupを使って画像urlを取得しlistViewで表示  
・同様にJsoupを使って価格と終了日を表示  
・listViewで表示される商品画像を大きく変更  
・正規表現でm.ebayをwww.ebayに変更(m.ebayではxmlpullparserがエラーをはくため)  
・ユーザーからの入力を受け付けるInputActivityを追加  
・intentで入力されたurlをMainActivityに送り、Downloaderでrssを取得し、随時parseし表示


CDATAセクションにはhtml tableがあるのでそれをlistViewで表示できればよいのですが試行錯誤中です。  
まだ未完成ですがオプションメニューからebayAPIを使った方法も実装しています。  
htmlリクエストを送信し、Json形式で商品リストを受け取るところまで動作を確認しています。

追加したい機能  
・csv出力  
・オークションタイプや現在価格でのソート等


　

