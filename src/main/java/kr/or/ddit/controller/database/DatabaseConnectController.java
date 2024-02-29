package kr.or.ddit.controller.database;

public class DatabaseConnectController {
	
	/*
	 * 11장: 데이터베이스 연동
	 * 
	 * 		1. Oracle 11g 설치(클라이언트, 서버)
	 * 
	 * 			- Oracle win32_lgR2_client와 OracleXE112_win64로 오라클 클라이언트, 서버를 설치
	 * 
	 * 			# 오라클 클라이언트 및 서버 설치 참고
	 * 			- https://junesker.tistory.com/79 (오라클 클라이언트 설치)
	 * 			- https://junesker.tistory.com/80 (오라클 서버 설치)
	 * 
	 * 		2. Oracle SQLDeveloper 설치	
	 * 		
	 * 			- sqldeveloper를 설치
	 * 			
	 * 			# 오라클 SqlDeveloper 설치 참고
	 * 			- https://junesker.tistory.com/81 (오라클 SqlDeveloper 설치)
	 * 
	 * 		3. 데이터 소스 설정
	 * 		
	 * 			- 애플리케이션이 데이터베이스에 접근하기 위한 추상화된 연결을 제공하는 역할을 한다.
	 * 
	 * 			# 스프링에서 설정할 수 있는 데이터 소스
	 * 			> JDBC 드라이버를 통해 선언된 데이터 소스
	 * 			> JNDI에 등록된 데이터 소스
	 * 			> 커넥션을 풀링하는 데이터 소스
	 * 			> DBCP2에 등록된 데이터 소스
	 * 
	 * 			*** JNDI란?
	 * 			- JNDI(Java Naming and Directory Interface)
	 * 			- 디렉토리 서비스에서 제공하는 데이터 및 객체를 발견(discover)하고 참고(lookup)하기 위한 자바 API이다.
	 * 
	 * 			# 데이터베이스 JDBC 라이브러리 설정
	 * 			- pom.xml에서 의존성 추가
	 * 
	 * 			# 데이터소스 설정
	 * 			- root-context.xml 설정
	 * 
	 *		4. CRUD 게시판
	 *			
	 *			- 데이터베이스 데이터에 접근하여 처리하는 방식
	 *
	 *			# 데이터베이스 처리방식
	 *			> 스프링 JDBC
	 *			> JPA
	 *			> 마이바티스
	 *
	 *				*** 우리는 마이바티스를 이용한 데이터 접근 처리로 CRUD게시판을 진행
	 *
	 *				1) 오라클 데이터베이스 계정 생성
	 *				2) 생성 SQL
	 *					create table board(
	 *						board_no number(8) not null,
	 *						title varchar2(200) not null,
	 *						content varchar2(4000) not null,
	 *						writer varchar2(50) not null,
	 *						reg_date date default sysdate null,
	 *						constraint pk_board primary key(board_no)
	 *					);
	 *
	 *					create sequence seq_board increment by 1 start with 1 nocache;
	 *
	 *				3) 작성할 화면(기본화면)
	 *					- 등록화면
	 *					- 등록 처리 후 화면
	 *					- 목록 화면
	 *					- 상세보기 화면
	 *					- 수정화면
	 *					- 수정 처리 후 화면
	 *					- 삭제 처리 후 화면 
	 *
	 *			5. 스프링 JDBC
	 *				- SQL 로만 데이터베이스를 쉽게 처리 할 수 있도록 도와주는 JDBCTemplate 클래스를 제공한다.
	 *
	 *				# JDBCTemplate 클래스가 제공하는 주요 메소드
	 *
	 *				- queryForObject
	 *					: 하나의 결과 레코드 중에서 하나의 컬럼 값을 가져온다
	 *				- queryForMap
	 *					: 하나의 결과 레코드 정보를 Map 형태로 매핑할 수 있다.
	 *				- queryForList
	 *					: 여러 개의 결과 레코드를 처리 할 수 있다.
	 *				- query
	 *					: ResultSetExtractor, RowCallbackHandler와 함께 조회할 때 사용한다
	 *				- update
	 *					: 데이터를 변경하는 SQL을 실행할 때 사용한다.
	 *
	 *				# 스프링 JDBC 설정
	 *		
	 *				- spring-jdbc 의존관계 설정
	 *					> 의존관계 설정에서 spring-jdbc 설정 처리함(pom.xml)
	 *
	 *				- 데이터소스 설정
	 *					> jdbcTemplate 클래스를 반으로 정의 (우리는 따로 하지 않음)
	 *
	 *				- 설정 예시
	 *					<bean id="jdbcTemplate" class="org.springframeword.jdbc.core.jdbcTemplate">
	 *						<property name="dataSource" ref="dataSource" />
	 *					</bean>
	 *
	 *				# 게시판 구현 설명
	 *				
	 *					기본적은 CRUD를 바탕으로 한 게시판 구현입니다
	 *					등록, 수정, 삭제, 상세보기 등의 페이지를 구현하여  JDBC 방식 기반의 CRUD를 작성합니다
	 *					JDBC의 방식은 기존 PrepareStatement를 활용한 방식과 동일하므로 넘어가도록 합니다!
	 *
	 *			6. JPA(Java persistence API)
	 *			
	 *				- JPA는 자바 표준 ORM입니다.
	 *				즉, 구현되어 있는 클래스에 테이블을 매핑하기 위한 프레임워크입니다.
	 *
	 *				# [장점]
	 *				- SQL 위주의 쿼리를 작성하지 않아도 된다.	
	 *				- 코드량이 엄청 줄어든다 (생산성)
	 *				- 객체위주의 코드 작성으로 가독성이 좋아진다 (생산성)
	 *				- 객체를 사용하는 시점에 쿼리를 전달하거나 트랜잭션 안에서 처리 내용의 같음을 보장한다 (패러다임의 불일치 해결)
	 *
	 *				# Entity
	 *				- 데이터베이스에서 지속적으로 저장된 데이터를 자바 객체에 매핑한 것이다.
	 *				- 메모리 상에 자바 객체의 인스턴스 형태로 존재하며 EntityManager에 의해 데이터베이스의 데이터와 동기화한다.
	 *				
	 *				# EntityManager
	 *				- 필요에 따라 Entity와 데이터베이스의 데이터를 동기화한다.
	 *				- EntityManager에서 제공하는 Entity 조작 API를 이용해 Entity에 대해 CRUD 작업을 할 수 있다.
	 *				
	 *				# Entity 상태
	 *				- new 상태
	 *				- 관리 상태
	 *				- 분리 상태
	 *				- 삭제된 상태
	 *
	 *				# JPA 관련 어노테이션
	 *				자바빈즈 클래스 객체가 곧 테이블 구조와 같은 형태이기 때문에, VO클래스 내 @Entity 어노테이션을 통해 해당 객체가
	 *				테이블과 같은 Entity임을 설정하고 @Table어노테이션으로 테이블명을 설정해주면 VO클래스는 데이터베이스의
	 *				생성되어 있는 테이블과 연결되기 위한 준비가 된다.
	 *				
	 *				ex)
	 *				@Entity
	 *				@Table(name="board")
	 *				public class Board{}
	 *
	 *				그리고 각 필드(멤버변수)에 @Column(name="board_no" 어노테이션으로 테이블 컬럼과 매핑 설정을 한다.
	 *				설정된 Entity들을 이용하여 jpa객체를 통해서 함수를 호출하면 (persist, find 등등) 호출된 함수를 통해
	 *				Spring Data JPA가 자동으로 Entity를 분석하여 함수에 따른 쿼리를 자동생성하여 결과를 실행, 조회, 수정
	 *				등을 처리해준다.
	 *				기존 SQL 쿼리를 이요하여 서비스를 처리할때에 필드가 추가되면 필드와 관련되어 있는 기능, sql, 추가된 필드
	 *				등을 수정해야하는 번거로움이 발생하지만 JPA는 필드만 추가후 테이블 정보과 매핑만 시켜주면 JPA가 자동으로
	 *				분석을 진행한다.
	 *				그리고 사용하는 데이터베이스 종류가 변경된다 하더라도 문제가 없다. (JPA는 Spl을 직접 건들이지 않고 함수와
	 *				같은 기능들로만 쿼리를 요청하고 데이터베이스와 통신하기 때문)
	 *
	 *				JPA는 JPA구현체인 hibernate를 이용하여 사용합니다. 그러기 위해선 관련 의존관계를 등록 후 사용해야합니다
	 *				SQL문을 이요하여 데이터베이스를 연동하여 데이터를 가공하는게 아닌, Methode를 통해 데이터베이스를 조작할 수
	 *				있다는 장점이 있어 객체모델을 이용하여 비즈니스 로직을 구성하는데에만 집중을 할 수 있다.
	 *
	 *				하지만! 프로젝트의 규모와 복잡도가 클 때는 설계가 잘 못되는 경우 해당 프로젝트의 품질이 떨어진다는 문제가 있다!
	 *				그만큼 설계라인의 정확성이 필요하고 여러 방면에서 정확한 검증이 제대로 끝나지 않으면 적용하기가 까다롭다
	 *
	 *				[용어]
	 *				*** ORM이란?
	 *					- 객체에 데이터를 읽고 쓰는 방법으로 관계형 데이터베이스에 데이터를 읽고 쓰는 기술입니다.
	 *				
	 *				*** 기업 면접에서 ORM은 뭐 쓰셨어요? 라는 질문을 받는 경우가 있습니다.
	 *					- 저는 ibatis를 사용했습니다, 저는 mybatis를 사용했습니다 와 같은 대답을 하면 됩니다.
	 *
	 *		7. 마이바티스
	 *		
	 *			- SQL과 자바 객체를 매핑하는 아이디어로 개발된 데이터베이스 접근용 프레임워크이다.
	 *		
	 *			** 마이바티스를 간단하게 살펴보고 MybatisController에서 자세하게 진행 예정!
	 *
	 *			# 의존 관계 정의
	 *			- pom.xml에 mybatis 설정을 위한 의존관계를 등록하도록 합시다!
	 *
	 *				> mybatis
	 *				> mybatis-spring
	 *				> spring-jdbc
	 *				> commons-dbcp2
	 *				> log4jdbc-log4j2-jdbc4
	 *				> ojdbc6 or ojdbc8
	 *
	 *			# 스프링과 마이바티스 연결 설정
	 *			- root-context.xml 설정
	 *	
	 *				> dataSource 설정
	 *				> sqlSessionFactory 설정
	 *				> sqlSessionTemplate 설정
	 *
	 *			# 마이바티스 설정
	 *			- webapp/WEB-INF/mybatisAlias/mybatisAlias.xml 설정
	 *
	 *			# 마이바티스 구현
	 *			- mapper 패키지 안에 Mapper 파일 구성(.xml)
	 *				> mapper 패키지안에 BoardMapper.xml 구성
	 *
	 *
	 */
}


















