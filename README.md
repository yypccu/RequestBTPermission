Note: This project is a sample for the question asked in the PTT forum.
這個專案是為了回答 PTT 上提出的問題所示範的程式碼。

Android 6.0 以後，Danger Permission 需要在執行期(runtime, 也就是 app 開始執行後)再次檢查是否有取得該權限之後才能使用，
6.0 以前的版本只需要在安裝時和使用者確認即可。

請仔細查看 MainActivity 中的 ContextCompat.checkSelfPermission() 和 onRequestPermissionsResult() 裡面的程式碼，
這些就是用來在執行期和使用者確認權限的部分。

官方文件請參考: https://developer.android.com/training/permissions/requesting
