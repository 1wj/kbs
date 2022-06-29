package com.digiwin.app.kbs.service.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.kbs.service.common.constant.QuestionResponseConst;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理问题清单返回页面json
 * @author Jiangzhou
 * @date 2022/05/17
 */
@Component
public class FrcJsonHandleTool {
    public static Map<Integer, String> statusMap = new HashMap<Integer, String>();
    public static Map<Integer, String> processStatusMap = new HashMap<Integer, String>();
    static {
        // 完成状态
        statusMap.put(1, "未开始");
        statusMap.put(2, "已完成");
        statusMap.put(3, "无需处理");

        // 处理状态
        processStatusMap.put(1, "未处理");
        processStatusMap.put(2, "已处理");
        processStatusMap.put(3, "无需处理");
    }
    /**
     * 通用解决方案
     *
     * @param knowledgeCustomize
     * @return
     */
    public JSONArray currencySolutModule(JSONObject knowledgeCustomize) {
        JSONArray module = new JSONArray();
        // 原因分析
        JSONObject reasonAnalysis = knowledgeCustomize.getJSONObject(QuestionResponseConst.REASON_ANALYSIS);
        JSONArray reasonAnalysisChild = new JSONArray();
        reasonAnalysisChild.add(addChildText("原因分析", reasonAnalysis.getString("reason_analysis_description"), 1));
        module.add(addStep("原因分析", 1, new JSONArray(), reasonAnalysisChild));

        // 计划安排
        JSONObject planArrangeInfo = knowledgeCustomize.getJSONObject(QuestionResponseConst.PLAN_ARRANGE_INFO);
        JSONArray planArrangeInfoChild = new JSONArray();
        planArrangeInfoChild.add(addChildTable(new String[] {"步骤", "处理人"},
                new String[] {"step_name", "liable_person_name"},
                planArrangeInfo.getJSONArray("plan_arrange"), 1));
        module.add(addStep("计划安排", 2, addSubTitle(planArrangeInfo, "process_date", "liable_person_name"),
                planArrangeInfoChild));

        // 临时措施分配
        JSONObject temporaryMeasureVerify =
                knowledgeCustomize.getJSONObject(QuestionResponseConst.TEMPORARY_MEASURE_EXECUTE_VERIFY);
        JSONArray temporaryMeasureChild = new JSONArray();
        temporaryMeasureChild.add(addChildTable(new String[] {"措施内容", "处理人"},
                new String[] {"measure_content", "liable_person_name"},
                temporaryMeasureVerify.getJSONArray("temporary_measure_execute_verify_detail"), 1));
        module.add(addStep("临时措施分配", 3, addSubTitle(temporaryMeasureVerify, "process_date", "liable_person_name"),
                temporaryMeasureChild));

        // 临时措施执行验证
        JSONArray temporaryMeasureVerifyChild = new JSONArray();
        temporaryMeasureVerifyChild.add(
                addChildTable(new String[] {"措施内容", "处理人", "执行说明",  "验证说明"},
                        new String[] {"measure_content", "liable_person_name", "execute_illustrate",
                                "verify_illustrate"},
                        temporaryMeasureVerify.getJSONArray("temporary_measure_execute_verify_detail"), 1));
        module.add(addStep("临时措施执行验证", 4, addSubTitle(temporaryMeasureVerify, "process_date", "liable_person_name"),
                temporaryMeasureVerifyChild));

        // 持久措施分配
        JSONObject lastingMeasureVerify =
                knowledgeCustomize.getJSONObject(QuestionResponseConst.LASTING_MEASURE_EXECUTE_VERIFY);
        JSONArray lastingMeasureChild = new JSONArray();
        lastingMeasureChild.add(addChildTable(new String[] {"措施内容", "处理人"},
                new String[] {"measure_content", "liable_person_name"},
                lastingMeasureVerify.getJSONArray("lasting_measure_execute_verify_detail"), 1));
        module.add(addStep("持久措施", 5, addSubTitle(lastingMeasureVerify, "process_date", "liable_person_name"),
                lastingMeasureChild));

        // 持久措施执行验证
        JSONArray lastingMeasureVerifyChild = new JSONArray();
        lastingMeasureVerifyChild.add(
                addChildTable(new String[] {"措施内容", "处理人", "执行说明",  "验证说明"},
                        new String[] {"measure_content", "liable_person_name", "execute_illustrate", "verify_illustrate"},
                        lastingMeasureVerify.getJSONArray("lasting_measure_execute_verify_detail"), 1));
        module.add(addStep("持久措施执行验证", 6, addSubTitle(lastingMeasureVerify, "process_date", "liable_person_name"),
                lastingMeasureVerifyChild));

        // 处理确认
        JSONObject processConfirmVerify =
                knowledgeCustomize.getJSONObject(QuestionResponseConst.PROCESS_CONFIRM_VERIFY);
        JSONArray processConfirmVerifyChild = new JSONArray();
        processConfirmVerifyChild.add(addChildText("处理确认说明", processConfirmVerify.getString("verify_illustrate"), 1));
        module.add(addStep("处理确认", 7, addSubTitle(processConfirmVerify, "verify_date", "verify_person_name"),
                processConfirmVerifyChild));
        return module;
    }

    /**
     * 8D解决方案
     * @param knowledgeCustomize
     * @return
     */
    public JSONArray edSolutModule(JSONObject knowledgeCustomize) {
        JSONArray module = new JSONArray();
        // 问题描述&组建团队
        JSONObject teamBuild = knowledgeCustomize.getJSONObject(QuestionResponseConst.TEAM_BUILD);
        JSONArray teamBuildChild = new JSONArray();
        teamBuildChild.add(addChildTable(new String[] {"步骤", "处理人"  },
                             new String[] {"step_name", "liable_person_name"},
                teamBuild.getJSONArray("plan_arrange"), 1));
        module.add(
                addStep("问题描述&组建团队", 1, addSubTitle(teamBuild, "process_date", "liable_person_name"), teamBuildChild));

        // 围堵措施
        JSONObject containmentMeasure = knowledgeCustomize.getJSONObject(QuestionResponseConst.CONTAINMENT_MEASURE);
        JSONArray containmentMeasureChild = new JSONArray();
        containmentMeasureChild.add(addChildTable(new String[] {"围堵场所", "处理人"},
                             new String[] {"containment_place", "liable_person_name"},
                containmentMeasure.getJSONArray("containment_measure_detail"), 1));
        module.add(addStep("围堵措施分配", 2, addSubTitle(containmentMeasure, "process_date", "liable_person_name"),
                containmentMeasureChild));

        // 围堵措施验证
        JSONObject containmentMeasureVerify =
                knowledgeCustomize.getJSONObject(QuestionResponseConst.CONTAINMENT_MEASURE_VERIFY);
        JSONArray containmentMeasureVerifyChild = new JSONArray();
        containmentMeasureVerifyChild
                .add(addChildTable(new String[] {"围堵场所", "处理人", "围堵说明",  "验证说明"},
                        new String[] {"containment_place", "liable_person_name", "containment_illustrate",
                                "verify_illustrate"},
                        containmentMeasureVerify.getJSONArray("containment_measure_verify_detail"), 1));
        module.add(addStep("围堵措施执行验证", 3, addSubTitle(containmentMeasureVerify, "process_date", "liable_person_name"),
                containmentMeasureVerifyChild));

        // 根本原因分析
        JSONObject keyReasonAnalysis = knowledgeCustomize.getJSONObject(QuestionResponseConst.KEY_REASON_ANALYSIS);
        JSONArray keyReasonAnalysisChild = new JSONArray();
        keyReasonAnalysisChild.add(addChildText("流出原因", keyReasonAnalysis.getString("outflow_reason"), 1));
        keyReasonAnalysisChild.add(addChildText("产出原因", keyReasonAnalysis.getString("output_reason"), 2));
        keyReasonAnalysisChild.add(addChildText("系统原因", keyReasonAnalysis.getString("system_reason"), 3));
        module.add(addStep("根本原因分析", 4, addSubTitle(keyReasonAnalysis, "process_date", "liable_person_name"),
                keyReasonAnalysisChild));

        //纠正措施
        JSONArray correctiveMeasure = knowledgeCustomize.getJSONArray(QuestionResponseConst.CORRECTIVE_MEASURE);
        JSONArray correctiveMeasureChild = new JSONArray();
        correctiveMeasureChild
                .add(addChildTable(
                        new String[] {"纠正内容", "纠正人", "纠正部门"},
                        new String[] {"corrective_content", "corrective_person_name", "corrective_department_name"},
                        correctiveMeasure, 1));
        module.add(addStep("纠正措施分配", 5, new JSONArray(), correctiveMeasureChild));

        //纠正措施执行验证
        JSONObject correctiveMeasureVerify =
                knowledgeCustomize.getJSONObject(QuestionResponseConst.CORRECTIVE_MEASURE_VERIFY);
        JSONArray correctiveMeasureVerifyChild = new JSONArray();
        correctiveMeasureVerifyChild
                .add(addChildTable(
                        new String[] {"纠正内容", "纠正执行说明", "纠正人",  "验证说明"},
                        new String[] {"corrective_content", "corrective_execute_illustrate", "corrective_person_name",
                                "verify_illustrate"},
                        correctiveMeasureVerify.getJSONArray("corrective_measure_verify_detail"), 1));
        module.add(addStep("纠正措施执行验证", 6, addSubTitle(correctiveMeasureVerify, "process_date", "liable_person_name"),
                correctiveMeasureVerifyChild));

        // 预防措施分配
        JSONArray preventionMeasure = knowledgeCustomize.getJSONArray(QuestionResponseConst.PREVENTION_MEASURE);
        JSONArray preventionMeasureChild = new JSONArray();
        preventionMeasureChild.add(addChildTable(
                new String[] {"预防措施", "执行人"}, new String[] {"prevention_measure_content",
                        "liable_person_name"},
                preventionMeasure, 1));
        module.add(addStep("预防措施分配", 7, new JSONArray(), preventionMeasureChild));

        // 纠正措施执行验证
        JSONObject preventionMeasureVerify =
                knowledgeCustomize.getJSONObject(QuestionResponseConst.PREVENTION_MEASURE_EXECUTE_VERIFY);
        JSONArray preventionMeasureVerifyChild = new JSONArray();
        preventionMeasureVerifyChild
                .add(
                        addChildTable(new String[] {"预防措施", "执行人", "措施执行说明",  "验证说明"},
                                new String[] {"prevention_measure_content", "liable_person_name",
                                        "prevention_measure_execute_illustrate", "verify_illustrate"},
                                preventionMeasureVerify.getJSONArray("prevention_measure_execute_verify_detail"), 1));
        module.add(addStep("纠正措施执行验证", 8, addSubTitle(preventionMeasureVerify, "process_date", "liable_person_name"),
                preventionMeasureVerifyChild));

        // 处理确认
        JSONObject questionAcceptanceInfo =
                knowledgeCustomize.getJSONArray(QuestionResponseConst.QUESTION_ACCEPTANCE_INFO).getJSONObject(0);
        JSONArray questionAcceptanceInfoChild = new JSONArray();
        questionAcceptanceInfoChild
                .add(addChildText("处理确认说明", questionAcceptanceInfo.getString("acceptance_description"), 1));
        module.add(addStep("处理确认", 9, new JSONArray(), questionAcceptanceInfoChild));

        return module;
    }

    /**
     * 一般解决方案
     *
     * @param knowledgeCustomize
     * @return
     */
    public JSONArray commonSolutModule(JSONObject knowledgeCustomize) {
        JSONArray module = new JSONArray();
        // 问题分配
        JSONObject questionProcessInfo =
                knowledgeCustomize.getJSONArray(QuestionResponseConst.QUESTION_PROCESS_INFO).getJSONObject(0);
        JSONArray ProcessInfoChild = new JSONArray();
        ProcessInfoChild.add(addChildText("分配要求", questionProcessInfo.getString("question_distribute_request"), 1));
        ProcessInfoChild.add(addChildTable(new String[] {"步骤", "负责人"},
                new String[] {"step_name", "process_person_name"},
                questionProcessInfo.getJSONArray("question_distribute_detail"), 2));
        module.add(addStep("问题分配", 1, addSubTitle(questionProcessInfo), ProcessInfoChild));
        // 任务处理验收
        JSONObject curbVerifyInfo =
                knowledgeCustomize.getJSONArray(QuestionResponseConst.CURB_VERIFY_INFO).getJSONObject(0);
        JSONArray curbVerifyChild = new JSONArray();
        curbVerifyChild.add(addChildText("任务要求", curbVerifyInfo.getString("curb_request"), 1));
        curbVerifyChild.add(
                addChildTable(new String[] {"处理场所", "处理人", "任务反馈", "系统内数量", "实际检查数量"},
                        new String[] {"curb_scene", "process_person_name", "curb_feedback", "system_qty", "actual_check_qty"},
                        curbVerifyInfo.getJSONArray("curb_verify_detail"), 2));
        module.add(addStep("问题处理验收分配", 2, addSubTitle(curbVerifyInfo), curbVerifyChild));
        // 任务关闭
        JSONObject questionClosure =
                knowledgeCustomize.getJSONArray(QuestionResponseConst.QUESTION_CLOSURE).getJSONObject(0);
        JSONArray questionClosureChild = new JSONArray();
        questionClosureChild.add(addChildText("问题总结", questionClosure.getString("question_summary"), 1));
        module.add(addStep("问题处理验收分配", 3, new JSONArray(), questionClosureChild));
        return module;
    }
    /**
     * 添加解决步骤
     *
     * @param stepName
     * @param sort
     * @param subTitle
     * @param child
     * @return
     */
    public JSONObject addStep(String stepName, int sort, JSONArray subTitle, JSONArray child) {
        return new JSONObject().fluentPut("value", stepName).fluentPut("key", "step" + sort)
                .fluentPut("sort", sort).fluentPut("subTitle", subTitle).fluentPut("child", child);
    }

    /**
     * 传入对应包含处理时间和处理人的json生成对应数据
     *
     * @param json
     * @return
     */
    public JSONArray addSubTitle(JSONObject json) {
        JSONArray subTitle = new JSONArray();
        JSONObject processDate = new JSONObject().fluentPut("key", "处理时间")
                .fluentPut("value", json.getString("process_date")).fluentPut("sort", 1);
        JSONObject processPersonName = new JSONObject().fluentPut("key", "负责人")
                .fluentPut("value", json.getString("process_person_name")).fluentPut("sort", 2);
        subTitle.add(processDate);
        subTitle.add(processPersonName);
        return subTitle;

    }

    /**
     * 传入对应包含处理时间和处理人的json以及对应的key生成对应数据
     *
     * @param json
     * @param processDateKey
     * @param processPersonKey
     * @return
     */
    public JSONArray addSubTitle(JSONObject json, String processDateKey, String processPersonKey) {
        JSONArray subTitle = new JSONArray();
        JSONObject processDate = new JSONObject().fluentPut("key", "处理时间")
                .fluentPut("value", json.getString(processDateKey)).fluentPut("sort", 1);
        JSONObject processPersonName = new JSONObject().fluentPut("key", "负责人")
                .fluentPut("value", json.getString(processPersonKey)).fluentPut("sort", 2);
        subTitle.add(processDate);
        subTitle.add(processPersonName);
        return subTitle;

    }

    /**
     * 添加模块child里的textjson
     *
     * @param textKey
     * @param textValue
     * @param sort
     * @return
     */
    public JSONObject addChildText(String textKey, String textValue, int sort) {
        return new JSONObject().fluentPut("key", textKey).fluentPut("value", textValue).fluentPut("type", "text")
                .fluentPut("sort", sort);
    }

    /**
     * 添加模块child里的table Json
     *
     * @param thTitle
     * @param thKey
     * @param tdValue
     * @param sort
     * @return
     */
    public JSONObject addChildTable(String[] thTitle, String[] thKey, JSONArray tdValue, int sort)
            throws RuntimeException {
        // 如果三个数组数量不等则传参有问题
        if (thTitle.length != thKey.length) {
            throw new RuntimeException("生成页面表格数据错误");
        }
        JSONObject tableJson = new JSONObject();
        tableJson.fluentPut("key", "step");
        tableJson.fluentPut("type", "table");
        tableJson.fluentPut("sort", sort);
        // 表头json
        JSONObject thJson = new JSONObject();
        thJson.fluentPut("key", "thead");
        JSONArray thJsonArr = new JSONArray();
        //执行状态
        int statusIndex = -1;
        //处理状态
        int processIndex = -1;
        for (int i = 0; i < thTitle.length; i++) {
            thJsonArr
                    .add(new JSONObject().fluentPut("key", thKey[i]).fluentPut("value", thTitle[i]).fluentPut("sort", i));
            if("执行状态".equals(thTitle[i])){
                statusIndex = i;
            }if("处理状态".equals(thTitle[i])){
                processIndex = i;
            }
        }
        thJson.fluentPut("value", thJsonArr);
        // 数据json
        JSONObject tdJson = new JSONObject();
        tdJson.fluentPut("key", "tbody");
        JSONArray tdJsonArr = new JSONArray();
        for (int i = 0; i < tdValue.size(); i++) {
            JSONObject json = tdValue.getJSONObject(i);
            JSONObject td = new JSONObject();
            for (int j = 0; j < thKey.length; j++) {
                if(statusIndex == j){
                    td.fluentPut(thKey[j], statusMap.get(json.getInteger(thKey[j])));
                }else if(processIndex == j){
                    td.fluentPut(thKey[j], processStatusMap.get(json.getInteger(thKey[j])));
                }else{
                    td.fluentPut(thKey[j], json.getString(thKey[j]));
                }

            }
            tdJsonArr.add(td);
        }
        tdJson.fluentPut("value", tdJsonArr);
        tableJson.fluentPut("value", new JSONArray().fluentAdd(thJson).fluentAdd(tdJson));
        return tableJson;
    }
}
