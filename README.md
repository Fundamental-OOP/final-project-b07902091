# Final-Project

# Music Reference
Cherry Metal by Arthur Vyncke | https://soundcloud.com/arthurvost
Creative Commons Attribution-ShareAlike 3.0 Unported
https://creativecommons.org/licenses/by-sa/3.0/deed.en_US
Music promoted by https://www.chosic.com/

# How to Turn your java file to .jar file in Visual Studio
1. Open your folder
2. Go to Java Project
3. Export
4. Select your Main class


# Compile以及啟動遊戲
- Compile
    - 我們的java程式都集中放在 src 這一個directory
    - 通過以下指令可對java程式進行編譯並將class檔存到指定的directory里
        - javac -cp . -sourcepath src/ -d bin/  src/*.java

- 啟動遊戲
    - Windows 
        - 對於使用Windows的玩家，我們將遊戲整體封裝成一個 .exe 檔
        - 玩家只需將點擊 .exe 檔，便可啟動遊戲
    - Linux OS
        - 對於Linux OS的玩家，可用以下指令啟動遊戲
            - java -cp bin/ Main
