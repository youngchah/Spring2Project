package kr.or.ddit.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AOPController {
	
	/*
	 * 	14장 AOP
	 *
	 * 		AOP란? 예시
	 *
	 * 		403호 현명이가 신입으로 프로젝트를 진행하고 있습니다.
	 * 		그러던 어느날, 현태 팀장님을 통해서 '현명아, 지금 개발중인 서비스 처리 속도좀 로그로 남겨주세요!'
	 * 		라는 부탁을 받습니다.
	 *
	 * 		현명이는 부탁받은 요구사항을 이행하기 위해서 본인이 만들고 있는 서비스 로직에서 처리 속도를 찍어볼
	 * 		메소드를 개발해 처리속도가 잘 나오는걸 확인합니다.
	 *		아주 기분이 좋아진 현명이는 현태 팀장님께로 달려갑니다.
	 *
	 *		그리고서 현태팀장님께 해당 내용을 보고하고 컨펌받습니다.
	 *		현태팀장님은 아주 긍정적인 검토안을 현명이한테 전달하면서 우리 서비스 전체에도 처리 속도를 모두
	 *		찍어주세요 라고 다시 업무를 지시받습니다.
	 *
	 *		현명이는 본인이 만들어 낸 각 기능별 서비스 로직에 하나하나씩 약 2천만개의 서비스 중,
	 *		30만개의 서비스에 적용하는 중에 의문을 갖습니다......
	 *
	 *			"아니 서비스 로직에서 제일 중요한 로직은 본래의 기능이 제일 중요하고 지금 내가 작성하는 로직은
	 *			옵션(부가기능)이 추가되는 그런거 아닌가? 이거 계속 해야하나?;;;"
	 *
	 *			"그럼 이걸 하나의 묶음으로 처리가 불가능할려나?... 집에 가고 싶네;;;"
	 *
	 *		하는 생각을 하게 됩니다.
	 *		여기서, 시간을 측정하고 권한을 체크하는 등의 기능은 옵션과 같은 부가기능으로 일종의 인프라 로직이라고
	 *		하는데, 이 인프라 로직은 애플리케이션 전 영역에서 나타날 수 있고, 중복코드를 만들어 내 개발의 효율성을
	 *		저하시키고 유지보수가 힘들어 질 수 있습니다.
	 *
	 *		이러한 인프라 로직은 아래처럼 하나의 관심사를 가질 수 있는데 이런 관심사들의 중복이 횡단으로 나타나는데,
	 *		이것을 가르켜 '횡단 관심사(Cross-Cutting Concern)' 라고 한다.
	 *
	 *			┌───────────────────────────────────────────────────────────────────────────────┐
	 *			│	[처리속도 측정]		[처리속도 측정]		[처리속도 측정]		[처리속도 측정]	│
	 *			│───────────────────────────────────────────────────────────────────────────────│
	 *			│	[비즈니스 로직]		[비즈니스 로직]		[비즈니스 로직]		[비즈니스 로직]	│													
	 *			│	[처리내용 로깅]		[처리내용 로깅]		[처리내용 로깅]		[처리내용 로깅]	│
	 *			└───────────────────────────────────────────────────────────────────────────────┘
	 *				로그인 기능			회원가입 기능			게시판 목록			게시판 등록....
	 *
	 *		이러한 횡단 관심사를 통해서 프로그래밍 하는것이 AOP라고 한다.
	 *
	 *		# 간단하게 보고 넘어가기 (용어정도)
	 *		- Aspect(에스펙트)
	 *		: AOP의 단위가 되는 횡단 관심사
	 *		- 횡단 관심사(Cross-Cutting Concern)
	 *		: 핵심(Core) 비즈니스 로직(메소드 실행 시작 시간 출력, 메소드 처리 후 시간 출력 등)과 다소 거리가
	 *			있지만, 여러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용(메소드 실행 시작 시간 출력 등)
	 *		- 횡단 관심사 분리(Separation Of Cross-Cutting Concern)
	 *		: 횡단 관심사에 해당하는 부분(메소드 실행 시작 시간 출력 등)을 분리해서 한 곳으로 모으는 것을 의미
	 *		- @Component
	 *		: @Aspect와 짝꿍
	 *		: component-scan시 "여기 봐주세요~ 저 여기 있어요~" 라는 의미
	 *		- JoinPoint
	 *		: 어드바이스가 적용될 수 있는 위치
	 *		- Advice
	 *		: 어떤 부가기능(메소드 실행 시작 시간 출력)을 언제 (삼겹살을 굽기 전(before)에 등)사용할지 정의
	 *			> Advice(부가기능)는 타겟을 감싸서 위장된 프록시가 실행되기 위한 시점에 따라 옵션이 나뉘어진다.
	 *			> 아래에서 각 시점을 알아봅시다
	 *			- Before : 조인포인트 전에 실행. (삼겹살을 굽기 전에)
	 *			- After : 조인포인트에서 처리가 완료된 후 실행 (삼겹살을 굽고 먹은 직후 실행)
	 *			- After Returning : 조인 포인트가 정상적으로 종료 후 실행
	 *			- After Throwing : 조인 포인트에서 예외 발생시 실행. 예외가 발생안되면 실행 안함.
	 *			- Around : 조인 포인트 전후에 실행(삼겹살을 굽기 직전과 먹인 직후 실행)
	 *
	 *-----------------------------------------------------------------------------------------------
	 *
	 *	#AOP(관점 지향 프로그래밍(Aspect Oriented Programming)
	 *		- 관점 지향 프로그래밍(Aspect Oriented Programming)을 의미하는 약자입니다.
	 *
	 *		1-1) 관점 지향 프로그래밍
	 *		
	 *			소스 코드의 여기저기에 흩어져 있는 횡단 관심사를 중심으로 설계와 구현을 하는 프로그래밍 기법이다.
	 *			간단하게 설명하면 관점 지향 프로그래밍은 횡단 관심사의 분리를 실현하는 방법이다.
	 *
	 *			- 횡단 관심사(Cross-Cutting Concern)
	 *			: 핵심 비즈니스 로직과 다소 거리가 있지만, 여러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용
	 *
	 *			- 횡단 관심사와 분리(Separation Of Cross-Cutting Concern)
	 *			: 애플리케이션을 개발할 때 횡단 관심사에 해당하는 부분을 분리해서 한 곳으로 모으는 것을 의미
	 *
	 *		1-2) AOP 개발 순서
	 *
	 *			(1) 핵심 비즈니스 로직에만 근거해서 코드를 작성한다.
	 *			(2)	주변로직에 해당하는 관심사들을 분리해서 따로 작성한다.
	 *			(3) 핵심 비즈니스 로직 대상 객체에 어떤 관심사들을 결합할 것인지를 설정한다.
	 *
	 *		1-3) AOP 사용 예(로.보.트.예)
	 *			- 로깅, 보안적용, 트랜잭션관리, 예외처리
	 *
	 *			** 우리는 그 중, 로깅을 처리할 예정
	 *
	 *		1-4) AOP 관련 용어
	 *			- Aspect : AOP의 단위가 되는 횡단 관심사에 해당한다
	 *			- 조인포인트(JoinPoint)
	 *				: 횡단 관심사가 실행될 지점이나 시점(메소드 실행이나 예외 발생 등)을 말한다
	 *				: 어디에 적용할 것인지 결정, 메소드/객체생성시/필드접근시 등등
	 *			- 어드바이스(Advice)
	 *				: 특정 조인 포인트에서 실행되는 코드로, 횡단 관심사를 실제로 구현해서 처리하는 부분이다.
	 *				: 어떤 부가기능을 구현할 것인지 결정
	 *					-Before, After, AfterReturning, AfterThrowing, Around
	 *			- 포인트컷(PointCut)
	 *				: 수 많은 조인 포인트 중에서 실제로 어드바이스를 적용할 곳을 선별하기 위한 표현식을 말함
	 *				: 어드바이스가 적용될 시점(표현식으로 작성)..
	 *				: AOP 처리에 의해 처리 흐름에 변화가 생긴 객체를 말합니다.
	 *				: 어떤 대상에 대해서 부가 기능을 설정할 것인지 결정
	 *
	 *		1-5) 스프링 지원 어드바이스 요형(부가기능)
	 *			- Before
	 *				> 조인 포인트 전에 실행된다.
	 *				> 예외가 발생하는 경우를 제외하고 항상 실행된다
	 *			- After Returning
	 *				> 조인 포인트가 정상적으로 종료된 후에 실행된다.
	 *				> 예외가 발생하면 실행되지 않는다.
	 *			- After Trowing
	 *				> 조인 포인트에서 예외가 발생했을 때 실행된다.
	 *				> 예외가 발생하지 않고 정상적으로 종료되면 실행되지 않는다.
	 *			- After
	 *				> 조인 포인트에서 처리가 완료된 후 실행된다.
	 *				> 예외 발생이나 정상 종료 여부와 상관없이 항상 실행된다.
	 *			- Around
	 *				> 조인 포인트 전후에 실행된다.
	 *			
	 *		1-6) AOP의 기능을 활용하기 위한 설정
	 *	
	 *			- 의존 관계 등록 (pom.xml 설정)
	 *				> aspectjrt(이미 등록되어 있음)
	 *				> aspectjweaver
	 *
	 *			- 스프링 AOP 설정
	 *				> root-context.xml 설정
	 *				: AOP를 활성화하기 위한 태그를 작성합니다.
	 *
	 *		## 스프링 AOP
	 *			스프링 AOP는 동작 시점이 여러가지가 있지만(컴파일 시점, 클래스 로딩 시점, 런타임 시점...)
	 *			그 중, 런 타임 시점에 프록시 객체를 생성 후 기능을 삽입
	 *			> 스프링 AOP의 프록시는 메소드의 오버라이딩(인터페이스 기반의 참조) 개념으로 동작 하므로,
	 *			메소드 실행 시점 시 동작 
	 *		
	 *	2. 포인트 컷 표현식
	 *
	 *		- execution 지시자에 대해서 알아봅시다.
	 *		
	 *		# 포인트 컷(PointCut)
	 *		- Advice가 실행될 지점을 표현하는 표현식
	 *
	 *			2-1) execution 지시자의 표현 방식
	 *			- execution 지시자를 활용해 포인트컷을 표현한 것입니다.
	 *
	 *			- 포인트 컷 표현 요소
	 *	예) execution(Board kr.or.ddit.service.IBoardService.BoardService*.read*(..))
	 *	
	     *         표현요소            	│    설명
    *      ────────────────────────────────────────
    *         execution        		│    지시자
    *         Board            		│    반환값
    *         kr.or.ddit.service   	│    패키지
    *         BoardService*      	│    클래스(타입)
    *         read*            		│    메소드
    *         (..)           		│    인수, 파라미터
    *      ────────────────────────────────────────
    *      
    *
    *         2-2) 포인트컷 표현식에 사용되는 와일드 카드
    *
    *         와일드카드   │    설명
    *      ────────────────────────────────────────────────────────────────────────────────
    *         *         │ 임의의 패키지 1개 계층을 의미하거나 임의의 인수 1개를 의미한다.
    *         ..        │ 임의의 패키지 0개 이상 계층을 의미하거나 임의의 인수 0개 이상을 의미한다.
    *         +         │ 클래스명 뒤에 붙여 쓰며, 해당 클래스와 해당 클래스의 서브 클래스,
    *                 	│ 혹은 구현 클래스 모두를 의미한다.
    *      ────────────────────────────────────────────────────────────────────────────────
    *      
    *         2-3) 포인트컷 표현식을 적용한 모습
    *   @Before("execution(* kr.or.ddit.servie.IBoardService.BoardService.*.*(..))")
    *   public void startLog(JoinPoint jp){
    *      log.info("startLog : " + jsp.getSignature());
    *   }
    *	
    *
    *	### AOP를 적용할 때 주의할 점 
    *		
    *		root-context.xml, servlet-context.xml에서 basePackage로 설정한 설정 값에 따라서
    *		객체로 설정된 패키지 하위 자원들이 빈으로 등록되는데, @Component, @Aspect가 선언되어 있는
    *		AOPController와 조인포인트로 설정한 Sevice 부분들이 basePackage로 설정된 하위 자원들의
    *		중복으로 빈등록이 되지 않아 AOP가 활성화되지 않는 문제가 발생할 수 있습니다.
    *		AOP를 적용시키는데에 있어서 패키지 구조 설계에 따른 중복으로 인행 빈등록이 되지 않는 오류를 최소화하기
    *		위해서는 퍼스펙티브를 스프링으로 변경 후, Project Explorer를 이용해 Spring Bean으로 등록되어
    *		있는 내역들 중, @Aspect가 적용되어 있는 AOPController가 빈으로 등록되어 있는지를 체크하고
    *		체크했다면 혹 중복되어 빈으로 등록되어 있지는 않은지를 체크하고, 각각의 빈들이 중복되어 만들어지지
    *		않고 정상적으로 만들어지는거라면 위빙 포인트가 생성되어 타겟이 실행되기 전 AopProxy가 활성화되어
    *		인터페이스 기반이 프록시로서 역할을 할 수 있도록 true인지를 체크한다.
    *
    *	### AOP 프록시
    *
    *		클라이언트가 요청한 요청을 타깃이 받기 전 실제 타겟인것처럼 위장해서 클라이언트의 요청을 받는다.
    *		여기서 등장하는 개념이 프록시인데, 프록시는 쉽게 말하면 대리자라고 생각하면 되는데 타겟으로 향하는
    *		요청을 중간에서 대리자인 프록시가 받아 선 처리 후 타겟으로 요청을 던져준다.
    *		그리고 응답으로 나가는 부분을 잡아야하는 처리가 필요 시, 응답을 잡아서 처리 후 최종 응답을 내보낸다.
    *
    *			[클라이언트]	>>>	[프록시]	>>>	[타겟]
    */
	
	/*
	 * 3. Before 어드바이스
	 * - 조인 포인트 전에 실행된다. 예외가 발생하는 경우만 제외하고 항상 실행된다.
	 */
	
	@Before("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("[@Before] StartLog");
		// getSignature() : 어떤 클래스의 어떤 메소드가 실행되었는지 보여줌.
		//					파라미터 타입은 무엇인지 보여줌
		log.info("[@Before] StartLog : " + jp.getSignature());
		// getArgs() : 전달된 파라미터 정보를 보여줌
		// 예)	[BoardVO[boardNo=127, title=개똥이,,,]]
		log.info("[@Before] StartLog : " + Arrays.toString(jp.getArgs()));
		
		/*
		 * 8. 메소드 정보 획득시 사용
		 */
		// 프록시가 입혀지기 전의 원본 대상 객체를 가져온다.
		Object targetObject = jp.getTarget();
		log.info("[@Before] targetObject : " + targetObject);
		
		// 프록시를 가져온다.
		Object thisObject = jp.getThis();
		log.info("[@Before] thisObject : " + jp.getThis());
		
		// 인수를 가져온다.
		Object[] args = jp.getArgs();
		log.info("[@Before] args.length : " + args.length);
		for(int i = 0; i < args.length; i++) {
			log.info("[@Before] args : " + args[i]);
		}
	}
	
	/*
	 * 4. After Returning 어드바이스
	 * - 조인 포인트가 정상적으로 종료 후에 실행된다. 예외가 발생하면 실행되지 않는다.
	 */
	
	@AfterReturning("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void logReturning(JoinPoint jp) {
		log.info("[@AfterReturning] logReturning");
		log.info("[@AfterReturning] logReturning : " + jp.getSignature());
	}
	
	/*
	 * 5. After Throwing 어드바이스
	 * - 조인 포인트에서 예외가 발생했을 때 실행된다.	예외가 발생하지 않고 정상적으로 종료하면 실행되지 않는다.
	 * 
	 */
	@AfterThrowing(pointcut = "execution(* kr.or.ddit.service.IBoardService.*(..))",
				throwing = "e")
	public void logException(JoinPoint jp, Exception e) {
		log.info("[@AfterThrowing] logException");
		log.info("[@AfterThrowing] logException : " + jp.getSignature());
		log.info("[@AfterThrowing] logException : " + e);
	}
	
	/*
	 * 6. After 어드바이스
	 * - 조인 포인트에서 처리가 완료된 후 실행된다.
	 */
	
	@After("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("[@After] endLog");
		log.info("[@After] endLog : " + jp.getSignature());
		log.info("[@After] endLog : " + Arrays.toString(jp.getArgs()));
	}
	
	/*
	 * Around 어드바이스
	 * - 조인 포인트 전후에 실행된다.
	 * 
	 * 			- ProceedingJoinPoint
	 * 			: around 어드바이스에서 사용함
	 * 
	 * 			스프링프레임워크가 컨트롤하고 있는 비즈니스로직 호출을 가로챔.
	 * 			책임이 Around 어드바이스로 전가됨
	 * 			그 정보를 스프링 컨테이너가 around 어드바이스 메소드로 넘겨주면 
	 * 			ProceedingJoinPoint 객체로 받아서 arount 어드바이스 컨트롤 시 활용할 수 있도록 해줌 
	 */
	
	@Around("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		// 메소드 실행 직전 시간 체킹
		long startTime = System.currentTimeMillis();
		log.info("[@Around] : " + Arrays.toString(pjp.getArgs()));
	
		// 메소드 실행
		Object result = pjp.proceed();	// 타겟 실행
		
		// 메소드 실행 직후 시간 체킹
		long endTime = System.currentTimeMillis();
		log.info("[@Around] timeLog : " + Arrays.toString(pjp.getArgs()));
				
		// 직후 시간 - 직전 시간 = 메소드 실행 시간
		log.info("[@Around] timeLog : " + pjp.getSignature());
		log.info("[@Around] 메소드 실행 시간 : " + (endTime - startTime));
		return result;
	}
	
	/*
	 * 8. 메소드 정보 획득
	 * - @Before 어노테이션이 붙은 메소드는 JoinPoint라는 매개변수를 통해 실행 중인 메소드의 정보를 획득할
	 * 수 있다.
	 */
}

	












	
	
	
	
	
