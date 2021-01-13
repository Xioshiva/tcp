.class public SecondTest
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
.limit stack 20000
.limit locals 100
.var 0 is n I
.var 1 is result Z
ldc 3
ldc 4
iadd
ineg
istore 0
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "lol"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

