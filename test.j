.class public SecondTest
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
.limit stack 20000
.limit locals 100
.var 0 is n I
.var 1 is result Z
ldc 3
istore 0
ldc 1
istore 1
ldc 0
istore 0
label_0:
ldc 8
iload 0
if_icmplt label_1
iload 0
getstatic java/lang/System/out Ljava/io/PrintStream;
swap
invokevirtual java/io/PrintStream/print(I)V
iload 0
ldc 1
iadd
istore 0
goto label_0
label_1:
return
.end method

