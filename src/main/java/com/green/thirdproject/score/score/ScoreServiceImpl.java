package com.green.thirdproject.score.score;

import com.green.fefu.entity.Score;
import com.green.fefu.entity.Student;
import com.green.fefu.entity.StudentClass;
import com.green.fefu.exception.CustomException;
import com.green.fefu.parents.ParentsUserMapper;
import com.green.fefu.parents.model.GetSignatureReq;
import com.green.fefu.score.model.*;
import com.green.fefu.score.module.RoleCheckerImpl;
import com.green.fefu.score.repository.ScoreRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import com.green.fefu.student.repository.StudentClassRepository;
import com.green.fefu.student.repository.StudentRepository;
import com.green.fefu.student.service.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.green.fefu.exception.LJH.LjhErrorCode.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl {
    private final ScoreMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final StudentMapper studentMapper;
    private final RoleCheckerImpl roleChecker;
    private final ParentsUserMapper parentsUserMapper;
    private final ScoreRepository repository;
    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;

    //점수 넣기
    public int postScore(InsScoreReq p) {
        MyUser user = authenticationFacade.getLoginUser();
        DelScore delScore = new DelScore();



        // 선생이 아닐때
        GetSignatureReq req = new GetSignatureReq();
        req.setStudentPk((p.getStudentPk()));
        req.setSemester(p.getSemester());
        req.setYear(p.getYear());
        delScore.setExam(p.getScoreList().get(0).getExam());
        delScore.setStudentPk(p.getStudentPk());
        delScore.setYear(p.getYear());

        Student student = studentRepository.getReferenceById(p.getStudentPk()) ;


        Score score = new Score();
//        repository.getReferenceById(user.getUserId());
//
//        score.setScId(repository.findStudentBy(p.getStudentPk()));
//        repository.findScoreBy(p.getScoreList().get(0).getMark());
//        repository.findExamBy(p.getScoreList().get(0).getExam());
//        repository.findNameBy(p.getScoreList().get(0).getName());
//        repository.findSemesterBy(p.getSemester());
//        repository.findYearBy(p.getYear());

        score.setExam(p.getScoreList().get(0).getExam());
        StudentClass scid = studentClassRepository.findByStuId(student);
        score.setScId(scid);
        score.setSemester(p.getSemester());
        score.setYear(Integer.toString(p.getYear()));
        score.setName(p.getScoreList().get(0).getName());
        score.setMark(p.getScoreList().get(0).getMark());

        roleChecker.checkTeacherRole();
        repository.save(score);
//        getDetail list3 = studentMapper.getStudentDetail(p.getStudentPk());
//        GetClassIdRes res = mapper.getClassId(user.getUserId(), p.getStudentPk());
//        // 담당 학급이 아닐때
//        if (!list3.getUClass().equals(res.getClassId())) {
//            throw new CustomException(SCORE_INSERT_STU_POST);
//        }
        //성적 있을시 성적 지우고 새로입력
        try {
            int res3 = mapper.delScore(delScore);
            // 기본 정보 삽입
            // scoreList 삽입
            if (p.getScoreList() != null && !p.getScoreList().isEmpty()) {
                parentsUserMapper.delSignature(req);
                return mapper.postScoreList(p);
            }
            return 1; // 기본 정보만 삽입된 경우
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(SCORE_INSERT_POST);
        }
    }
    //점수 받아오기
    public Dto getScore(StuGetRes p){
        //학생, 학년, 년도, 학기, 과목(이름), 점수
        Dto dto = new Dto();
        StuGetRes res = mapper.getStu(p.getStudentPk());
        //학생의 점수 PK, 학생 PK, 직전 학년, 학기, 년도
        if (p.getLatestSemester() == 0) { //학기가 0이면
            Integer latestSemester = res.getLatestSemester();
            if (latestSemester == null || latestSemester == 0) {
                p.setLatestSemester(1);
            }
        }
        RankReq rank = new RankReq();

        rank.setStudentPk(res.getStudentPk());

        rank.setExam(p.getExam());

        rank.setGrade(res.getLatestGrade());

        rank.setSemester(res.getLatestSemester());

        log.info("resExam : {}", p.getExam());

        log.info("resSemester : {}", res.getLatestSemester());

        dto.setStudentPk(res.getStudentPk());

        dto.setLatestGrade(res.getLatestGrade());

        dto.setLatestSemester(res.getLatestSemester());

        dto.setLatestYear(res.getLatestYear());

        System.out.println(dto.getList().toString());

        p.setLatestGrade(res.getLatestGrade());

        res.setExam(p.getExam());

        //싸인 조회

        SignResult sign = new SignResult();
        sign.setStudentPk(res.getStudentPk());
        sign.setExamSign(p.getExam());
        sign.setSemester(res.getLatestSemester());



        if((res.getLatestSemester() == 1 || res.getLatestSemester() == 2)&& res.getExam() == 1){
            List<InsScoreList> list = mapper.getScoreMidterm(res);
            RankRes resMid = mapper.rankListMid(rank);
            dto.setClassRank(resMid);
            dto.setSignResult(mapper.signResult(sign));
            dto.setList(list);
//
        }else if((res.getLatestSemester() == 1 || res.getLatestSemester() == 2)&&res.getExam() == 2){
            List<InsScoreList> list1 = mapper.getScoreFinal(res);
            RankRes resFinal = mapper.rankListFinal(rank);
            dto.setClassRank(resFinal);
            dto.setSignResult(mapper.signResult(sign));
            dto.setList(list1);

        }else{
            return null;
        }
        return dto;
    }

    // 디테일하게 조회 EX 학년 학기
    public  DtoDetail getDetailScore(GetDetailScoreReq p){
        RankReq rank = new RankReq();
        StuGetRes res = mapper.getStu(p.getStudentPk());

        DtoDetail dto = new DtoDetail();


        SignResult sign = new SignResult();
        sign.setStudentPk(p.getStudentPk());
        sign.setExamSign(p.getExam());
        sign.setSemester(p.getSemester());

        rank.setStudentPk(p.getStudentPk());
        rank.setGrade(p.getGrade());
        rank.setExam(p.getExam());
        rank.setSemester(p.getSemester());

        log.info("studentPk: {}", rank.getStudentPk());
        log.info("semester: {}", rank.getSemester());
        log.info("grade: {}", rank.getGrade());
        log.info("exam: {}", rank.getExam());

        if ((p.getSemester() == 1 || p.getSemester() == 2) && p.getExam() == 1 ) {
//            dto.setClassRank(mapper.rankListMid(rank.getSemester()));
//            dto.setClassRank(mapper.rankListMid(rank.getExam()));
//            dto.setClassRank(mapper.rankListMid(rank.getGrade()));
            dto.setList(mapper.getDetailScore(p));
            dto.setSignResult(mapper.signResult(sign));
            dto.setClassRank(mapper.rankListMid(rank));
        }else if((p.getSemester() == 1 || p.getSemester() ==2) && p.getExam() ==2 ) {
            dto.setClassRank(mapper.rankListFinal(rank));
            dto.setSignResult(mapper.signResult(sign));
            dto.setList(mapper.getDetailScoreFinal(p));
        }
        if(res.getLatestGrade() < p.getGrade()){
            throw  new CustomException(SCORE_GET_VIEW);
        }
        if(dto.getList() == null || dto.getList().size() == 0){
            throw new CustomException(SCORE_GET_LIST_VIEW);
        }
        dto.setStudentPk(p.getStudentPk());
        return dto;
    }
}
