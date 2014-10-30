::@echo off

set cmd=C:\Program Files\Java\jdk1.6.0_17\bin
set dest2=C:\tmp\cvsout2\UWS-MTPP\build\classes\dataprotect\res

"%cmd%\native2ascii" china_sb.txt SBBundle_zh_CN.properties

REM picture and lang 
set res_path=C:\Work\project\Resource\uws-mtpp
set res_dest_path=%dest2%

set opt=/s /e /y
xcopy %res_path%  %res_dest_path%\  %opt%

copy SBBundle_zh_CN.properties  %dest2%\SBBundle_zh_CN.properties
copy SBBundle_en_US.properties  %dest2%\SBBundle_en_US.properties
copy SBBundle_ja_JP.properties  %dest2%\SBBundle_ja_JP.properties
