# FOOP Final Project Report
## 組員
|姓名|學號|負責事項|貢獻度|
|-|-|-|-|
|張翔文|B07902109|||
|李智源|B07902089|||
|周俊廷|B07902091|||
|陳君翰|B07902059|||
## Classes之間的關係
- 下列關係圖原圖都有放在Repo
- State Machine
    - ![](https://i.imgur.com/Tbk555M.png =450x430)
- Class Relation
    - ![](https://i.imgur.com/gTP7WSC.png =500x500)

## 設計
- 優點
    - 新增角色、技能非常方便，沒有破壞助教原本寫的扣的OCP
    - 關係圖清楚，方便別人閱讀code
    - 圖片、音訊和Source Code分離，方便區隔占空間的資料夾，push上Github時使用LFS也較為方便
    - 使用CardLayout切換畫面(JPanel)，新增畫面或刪除畫面簡單
- 缺點
    - 由於使用Swing來設計UI，調整版面相比Web困難許多

## 其他使用的packages

- 只有使用awt, util, io等原生的package

## 遊戲畫面
- JFrame: GameView.java使用CardLayout
1. 主頁選單畫面(Intro.java):玩家可按空白鍵開始遊戲或退出遊戲
    - **START**: 按下空白鍵後開始遊戲且進入角色選擇畫面
    - **QUIT** button：退出遊戲並且關閉視窗
2. 角色選擇畫面(CharacterMenu.java)：兩位玩家可以各自選擇兩個角色
    - 玩家可點擊附有角色icon的button來選擇角色
    - **FIGHT** button:當兩個玩家各自選擇兩個角色時，此button會被enabled，點擊之後可進入場景選擇畫面，否則是disabled狀態
3. 場景選擇畫面(StageMenu.java)：玩家可選擇打鬥場景
    - 玩家可點擊附有場景icon的button來選擇場景
    - **START** button:當玩家選擇場景之後可以點擊此button開始遊戲，如果沒選擇場景，點此button後會選擇default場景
    
4. 遊戲戰鬥畫面(Canvas.java)：玩家戰鬥時的畫面
    - 此畫面會顯示兩個玩家的當前各自派出的角色並且戰鬥
    - **Box** button：玩家可透過此按鈕來決定是否開啟戰鬥時的Bounding Box(偵測角色邊框以及技能邊框)
    - 每個人角色會有三個Bar分別為:
        1) Health Point Bar:顯示該角色當前的Health Point
            - 綠色範圍：殘餘Health Point
            - 紅色範圍:Max health Point -  殘餘Health Point
        2) Magic Point Bar：顯示該角色當前的Magic Point
            - 深藍色範圍:殘餘Magic Point
            - 灰色範圍:Max Magic Point -  殘餘Magic Point
        3) Ultimate Point Bar:顯示該角色當前的Ultimate Point
            - 淺藍色範圍:殘餘Ultimate Point
            - 灰色範圍:Max Ultimate Point -  殘餘Ultimate Point

5. 遊戲結束畫面:遊戲結束後會彈到此畫面詢問玩家是否繼續遊玩
    - **Yes** button:跳到角色選擇畫面
    - **No** button：跳到主頁選單畫面

### 遊戲流程
1. 在主頁選單畫面選擇開始遊戲然後進入角色選擇畫面或者退出遊戲並且關閉視窗
2. 在角色選擇畫面兩個玩家可用滑鼠點擊角色的icon來選取角色，當兩位玩家都各自選擇了兩個角色便可進入場景選擇畫面
3. 在場景選擇畫面玩家可選擇任一場景進行戰鬥，選擇之後可進入遊戲戰鬥畫面
4. 在遊戲戰鬥畫面中，玩家可根據不同的角色使出不同技能並且對敵方照成傷害，直到某方的兩個角色都死亡遊戲便結束，進入遊戲結束畫面
5. 在遊戲結束畫面，玩家可選擇是否繼續遊玩，是的話進行角色選擇畫面，否則就進入主頁選單畫面

#### 遊戲畫面流程圖
![](https://i.imgur.com/bX9Ncdp.png =450x520)

### 遊戲規則
1. 這遊戲**必須**有兩個玩家才可進行
2. 每個玩家必須各自選擇兩個角色，否則**FIGHT** button會是**disabled**的狀態
3. 場景選擇頁面如果玩家沒選擇場景，會使用**預設場景**
4. 一個玩家每次只能派出**一個角色**進行戰鬥
5. 當Health Point <=0時，該角色判定為死亡，玩家會自動派出另外一個還未死亡的角色，如果兩個角色都死亡則遊戲結束，另一個玩家勝利
6. 遊戲每秒會使當前戰鬥中角色的Magic Point自動增加5
7. 每當角色使用punch/kick攻擊敵對並且成功對敵對造成傷害，該角色的Ultimate Point 會增加50
8. 當角色當前的Magic Point>=first skill 的required Magic Point，才可以使用first skill
9. 當角色當前的Magic Point>=second skill 的required Magic Point，才可以使用second skill
10. 當角色當前的Magic Point>=ultimate skill 的required Magic Point且Ultimate Point>=Max Ultimate Point，才可以使用 ultimate skill
11. 當玩家只剩下一個角色，另一個角色死亡時，玩家無法使用change character這個功能


### Character的attribute
1. 每個角色出拳和踢腳的Damage Box都不相同，各有特色
4. First skill：每個character有專屬的first skill,每個first skill required的Magic Point都不同， 對敵人造成的傷害也不同
5. Second skill：每個character有專屬的second skill,每個second skill required的Magic Point都不同， 對敵人造成的傷害也不同
6. Ultimate skill: 每個character有專屬的ultimate skill, 每個ultimate skill required的Magic Point都不同，對敵人造成的傷害也不同, 但都需要該角色Ultimate Point達到最大值才可以使用 
1. Health Point: 預設值為500， 最大值為500
2. Magic Point: 預設值為200， 最大值為200
3. Ultimate Point: 預設值為0, 最大值為200

### Character
- Emily
    - ![](https://i.imgur.com/BdrSS13.png)
    - 特性: 穿著治癒術士的制服降低敵人的警戒，再給對方突如其來的一擊！
    - 技能: LightningBolt(100mp,50damage), Lightning(150mp,100damage), **IceWall**(ultimate skill,200damage)
    - 故事: 小時候夢想成為受人歡迎的治癒術士，平常最大的愛好就是收集治癒術士的制服，沒想到治癒術士公會不接受男性加入，於是從此踏上了反抗世界的征途

- Gray
    - ![](https://i.imgur.com/5NmS3Hm.png)
    - 特性: 蛤？Nazi算哪根蔥？我才是真的火龍王！
    - 技能: FireBall(125mp,100damage), Fire(150mp,150damage), **FireRing**(ultimate skill,200damage)
    - 故事: 曾經是納茲最忠實的粉絲，一心只鑽研究極的火焰魔法，在一場比賽中發現Nazi使用紫電後大受打擊，誓言要打到Nazi這個偽君子

- Alita
    - ![](https://i.imgur.com/CvIkfuX.png)
    - 特性: 機械族的少女，能夠高速且悄無聲息的移動，可靠又可怖的殺手
    - 技能: LightningBolt(50mp 50damage), FireBall(100mp 100damage),  **AtomicBomb(LightningBolt + FireBall)**(ultimate skill,150damage)
    - 故事: 甦醒於失落國度的古遺跡，除了知道自己的名字之外已喪失所有記憶，僅有額頭上的寶珠作為線索，發誓要找到王國隕落的真相

- Nazi
    - ![](https://i.imgur.com/fJMj4fS.png =100x120)
    - 特性: 如果有一顆火球解決不了的事情，那...可以考慮紫電（？
    - 技能: LightningBolt(50mp,50damage), FireBall(100mp,100damage), **SuperFireBall**(ultimate skill,100 damage)
    - 故事: 原本是最強的烈焰魔導士，但在某次眾神的聚宴中惹惱了天空之神因而受到懲罰，失去大部分操控火焰的能力，但也意外的獲得紫電的技能？


### Skills
- 圖片以Casting->Flying->Triggered的順序來呈現
- FireBall
    - ![](https://i.imgur.com/qkTOF95.png)
- Fire(無trigger動畫)
    - ![](https://i.imgur.com/ogYEQ3F.png)
- FireRing
    - ![](https://i.imgur.com/4dmcI4m.png)
- IceWall(實際遊玩時冰柱會變成5個連在一起)
    - ![](https://i.imgur.com/4VwGfdE.png)
- Lightning(無trigger動畫)
    - ![](https://i.imgur.com/gDpbSjV.png)
- LightningBolt
    - ![](https://i.imgur.com/K0PzEz9.png)


### Key-binding

#### Player 1:
- Control
    - **W, A, S, D**: jump, left, crouch, right
    - **F**: punch, **G**: kick, **E**: change character
- Combo:
    - **S+S+F**: first skill of character
    - **S+S+G**: second skill of character
    - **S+A/D+F+G**: ultimate skill of character

#### Player 2:
- Control
    - **ArrowKey_UP, LEFT, DOWN, RIGHT**: jump, left, crouch, right
    - **K**: punch, **L**: kick, **P**: change character
- Combo:
    - **DOWN+DOWN+K**: first skill of character
    - **DOWN+DOWN+L**: second skill of character
    - **DOWN+LEFT/RIGHT+K+L**: ultimate skill of character

## 組員心得

## 詳細資料

- Github連結: 
https://github.com/Fundamental-OOP/final-project-b07902091

- Music Reference
Cherry Metal by Arthur Vyncke | https://soundcloud.com/arthurvost
Creative Commons Attribution-ShareAlike 3.0 Unported
https://creativecommons.org/licenses/by-sa/3.0/deed.en_US
Music promoted by https://www.chosic.com/