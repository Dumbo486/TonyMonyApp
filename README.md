# TonyMonyApp
톤팡질팡은 이제 그만! 나만의 퍼스널컬러를 찾아주는 앱 (대학교 2학년 모바일 애플리케이션개발 수업 과정)

## 주요 화면 및 기능 소개 
![image](https://user-images.githubusercontent.com/48663295/77520748-003f9100-6ec5-11ea-910d-ef69234e6129.png)
### 초기화면 - 피부톤 타입별로 간단한 설명과 어울리는 색상 팔레트 제시 
- GridView : 홈 화면에서 4가지  skin에 대해 설명
- Data class : skin 이라는 클래스 생성
( 계절, 웜or쿨, 이미지->R.drawable….)
- Custom Adapter : Custom 하게 만든 custom한 grid item에 대한 어댑터
![image](https://user-images.githubusercontent.com/48663295/77520895-39780100-6ec5-11ea-8123-55d01461dea2.png)
### 퍼스널컬러 진단 화면 - 카메라 또는 앨범에 사진을 불러옴
- 파일 입출력 기능 : 외부 저장소 생성, Personal color라는 디렉토리 만들어 저장 및 해당 경로 불러오기
* 사진 불러올 때 CAMERA에서 불러오는 기능은 에뮬레이터에서는 실행 불가
* 핸드폰에서 실행 가능
![image](https://user-images.githubusercontent.com/48663295/77520936-47c61d00-6ec5-11ea-99ba-7d6ecbb368b2.png)
### 헤어, 눈동자, 볼 버튼 클릭 후 이에 해당하는 구역을 직접 클릭 / 자신의 정보 등록 및 퍼스널 컬러 확인
- 비트맵 이미지에서 머리, 눈동자, 피부의 r,g,b 값을 추출해 h,s,v 로 변환해 퍼스널컬러를 구분할 수 있는 함수를 작성
( onTouchListener 를 이용해서 해당 좌표의 r,g,b 값을 받음)
- Data class : RGBtoHSV라는 클래스 생성
( r,g,b,h,s,v 클래스 멤버를 갖고 있음)
데이터 추가 : 버튼을 누르고 사진을 클릭할 때마다  데이터 값이 추가됨  
![image](https://user-images.githubusercontent.com/48663295/77521169-97a4e400-6ec5-11ea-9305-1dd380f0cf8e.png)
### 퍼스널컬러에 따른 컬러 팔레트와 헤어 컬러를 추천하는 화면 제시 (이외의 상품에 대한 추천화면 미완성)
- users table을 create 하여 진단 검사를 끝낼 때마다 사용자 정보가 table row에 추가된다. (id,name,sex,tone)
- SQLiteOpenHelper를 이용하여 데이터베이스를 생성하였다.
![image](https://user-images.githubusercontent.com/48663295/77522392-8a88f480-6ec7-11ea-9659-e22a18a63e72.png)
### 개발자 사이트 및 추천상품 홈페이지 화면 
- 멀티쓰레드  : 블로그 rss 값을 가져와 리스트뷰로 보여줄 때 Thread 사용
- 웹과의 통신 : 블로그 rss 값 가져와 보여주기 또는 해당하는 웹 사이트의 링크로 이동하기

## 보완해야할 사항
- tone 에 따른 item을 추천 기능 구현 실패  : 원래는 item이라는 메뉴를 추가해서 톤별로 추천 화장품을 테이블에 생성해놓고 GridView로 보여주려고 했으나, 웹 이미지 경로를 테이블에 저장하는 데 오류가 많이 발생하여 item 메뉴 자체를 포기함
- TabHost 를 이용해서 하나의 Tab 아래 화면에서도 여러개의 Fragment를 자유롭게 전환하는 기능을 구현하고 싶었지만 Tabhost에서  FragmentTranaction을 구현하는데 어려움을 겪어 TabHost 대신 메뉴로 기능들을 표시
- My Palette 라는 메뉴의 Fragment를 호출할 때 , onCreate에서 다른 fragment에서 넘긴 인자를 받는다. 인자를 받는 과정이 포함되어야해서 My Palette라는 메뉴를 퍼스널 컬러 진단하기 전에 접속하면 에러가 발생한다. 
- DB 테이블에 많은 이미지를 저장하고 싶어서, 이미지를 바이너리 형식으로 저장하고 싶었으나 실질적으로 구현 실패
