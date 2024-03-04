package kr.or.ddit.controller.tiles;

public class TilesSettingsController {
	/*
	 * ===================== 부트스트랩을 이용한 CRUD 진행해봅니다 ======================
	 * 
	 * 		** 페이지 모듈화를 위한 Tiles를 함께 사용하여 CRUD를 진행합니다.
	 * 
	 * 		1. Tiles란?
	 * 		- 어떤 jsp를 템플릿으로 사용하고 템플릿의 각 영역을 어떤 내용으로 채울지에 대한 정보를 설정한다.
	 * 		- 하나의 화면들을 만들다보면 공통적이고 반복적으로 생성해야하는 header, footer와 같은 영역들이 존재합니다.
	 * 		우리는 그러한 공통부분들을 분리하여 반복적으로 컴포넌트들을 사용하는게 아닌 공통적인 부분은 한번만 가져다 쓰고
	 * 		변화하는 부분에 대해서만 동적으로 변환해 페이지를 관리 할 수 있어야할 것입니다.
	 * 		이렇게, header/footer/menu/side 등 공통적인 소스를 분리하여 한 화면에서 동적으로 레이아웃을 한곳에
	 * 		배치하여 설정하고 관리 할 수 있도록 도와주는 페이지 모듈화를 돕는 프레임워크입니다.
	 * 
	 * 		- 아래 jsp들을 이용하여 페이지 모듈화를 진행
	 * 		> template.jsp : 대표적인 background 페이지 (페이지 모듈화의 배경지가 될 페이지)
	 * 		> header.jsp : 공통으로 중복되어 나올 헤더 페이지
	 * 		> content source : content는 동적으로 변화할 페이지
	 * 		> footer.jsp : 공통적으로 중복되어 나올 헤더 페이지 
	 * 
	 * 			** 그 외에 다양한 영역의 페이지는 구현하고자 하는 시나리오를 바탕으로 페이지가 구성될 때
	 * 			추가적으로 레이아웃 영역을 분리하여 작성하면 됩니다.
	 * 
	 * 		2. Tiles Layout 구현 설명
	 * 
	 * 			1) Tiles 의존 관계 등록
	 * 			- tiles-core
	 * 			- tiles-api
	 * 			- tiles-servlet
	 * 			- tiles-jsp
	 * 			** 의존 관계 등록 후 Maven > update Projects (프로젝트에 반영)
	 * 
	 * 			2) servlet-context.xml 수정
	 * 			- ViewResolver order 순서 2순위로 지정
	 * 			- tilesViewResolver Bean 등록 진행
	 * 			
	 * 			3) tiles 설정 위한 xml 생성
	 * 			- /WEB-INF/spring/tiles-config.xml
	 * 
	 * 			4) tiles xml에 설정한 layout 설정대로 페이지 생성(jsp)
	 * 
	 * 		3. 공지사항 게시판(부트스트랩 디자인) 실습
	 * 
	 * 			3-1) 데이터 베이스 준비
	 * 				create table notice(
	 * 					bo_no number(8) not null,
	 * 					bo_title varchar2(300) not null,
	 * 					bo_content varchar2(4000) not null,
	 * 					bo_writer varchar2(200) not null,
	 * 					bo_date date not null,
	 * 					bo_hit number(8) default 0 null,
	 * 					constraint pk_notice primary key(bo_no)
	 *				 );
	 *
	 *				create sequence seq_notice increment by 1 start with 1 nocache;
	 *
	 *			3-2) 게시판 작성
	 *			- 게시판 목록, 상세보기 컨트롤러 만들기(notice/NoticeRetrieveController)
	 *			- 게시판 목록 화면 컨트롤러 메소드 만들기(noticeList:get)
	 *			- 게시판 목록 화면 만들기(notice/list.jsp)
	 *			- 게시판 등록 컨트롤러 만들기 (notice/NoticeInsertController)
	 *			- 게시판 등록 화면 컨트롤러 메소드 만들기 (noticeForm:get)
	 *			- 게시판 등록 화면 만들기 (notice/form.jsp)
	 *			- 여기까지 확인 
	 *
	 *			- 게시판 등록 기능 컨트롤러 메소드 만들기
	 *			- 게시판 등록 기능 서비스 인터페이스 메소드 만들기
	 *			- 게시판 등록 기능 서비스 클래스 메소드 만들기
	 *			- 게시판 등록 기능 Mapper 인터페이스 메소드 만들기
	 *			- 게시판 등록 기능 Mapper xml 쿼리 만들기
	 *			- 여기까지 확인 
	 *
	 *			- 게시판 상세 화면 컨트롤러 만들기 (NoticeRetreiveController에 있음)
	 *			- 게시판 상세 화면 컨트롤러 메소드 만들기 (noticeDetail:get)
	 *			- 게시판 상세 화면 서비스 인터페이스 메소드 만들기
	 *			- 게시판 상세 화면 서비스 클래스 메소드 만들기
	 *			- 게시판 상세 화면 Mapper 인터페이스 메소드 만들기
	 *			- 게시판 상세 화면 Mapper xml 쿼리 만들기
	 *			- 게시판 상세 화면 만들기 (notice/detail.jsp)
	 *			- 여기까지 확인 
	 *			
	 */
}














