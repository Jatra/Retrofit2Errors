# Retrofit2Errors
Basic Retrofit2/RxJava2

With an illustration of the different types of errors.

Also worth attempting to run the app when airplane mode is on, or using Charles to tamper with the response and make it invalid.

If the Json is malformed (eg a stray ,) then a MalformedJsonException will be thrown (an IOException)

If the connection times out, that's also an IOException, eg a SocketTimeoutException

## Notes
SubscribeOn(Schedulers.io()) appeared to be needed ona  test handset running ANdroid 9, but not on an Android 10 emulator.