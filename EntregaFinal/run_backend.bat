@echo off
cd /d "D:\UCC2\EntregaFinal"
set JAVA_HOME=C:\Program Files\Java\jdk-17
set MONGODB_URI=mongodb+srv://RexbilXD:CDrexbilBC2040%%2B@plataformaregistrovolun.alzk2s5.mongodb.net/voluntariado?retryWrites=true^&w=majority
set JWT_SECRET=egrf9CK5k2Iu+392cO3LobrE5obOH7vNogVZubUeC/0=
"%JAVA_HOME%\bin\java.exe" -jar target\plataforma-registro-voluntariado-0.0.1-SNAPSHOT.jar
pause