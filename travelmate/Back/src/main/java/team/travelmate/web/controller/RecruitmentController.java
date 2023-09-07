package team.travelmate.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.travelmate.common.SearchCondition;
import team.travelmate.domain.Entity.recruitment.Recruitment;
import team.travelmate.web.returnjson.DeleteResult;
import team.travelmate.web.service.RecruitmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/recruitments")
@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @GetMapping
    public Object getRecruitments(
            @Validated @RequestBody SearchCondition con,
            BindingResult bindingResult,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
            ){

        if (bindingResult.hasErrors()){
            log.info("검증 오류 발생 error = {}",bindingResult);
            return bindingResult.getAllErrors();
        }

        Map<String,List<Recruitment>> result = new HashMap<>();

        List<Recruitment> recruitments = recruitmentService.findRecruitments(con, page,size);

        log.info("con check : [title] : {}", con.getTitle());

        for (Recruitment r: recruitments) {
            log.info("title : {}" , r.getTitle());
        }
        result.put("RECRUITMENT",recruitments);
        return result;
    }

    @DeleteMapping("/{Rid}")
    public DeleteResult deleteRecruitment(@PathVariable Long Rid){
        return recruitmentService.deleteRecruitment(Rid);
    }

}
