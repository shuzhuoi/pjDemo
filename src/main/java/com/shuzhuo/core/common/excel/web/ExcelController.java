package com.shuzhuo.core.common.excel.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.common.config.ApplicationYmlConfig;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.excel.util.MyMockMultipartFile;
import com.shuzhuo.core.common.exception.CustomException;
import com.shuzhuo.core.system.attachment.entity.Attachment;
import com.shuzhuo.core.system.attachment.service.IAttachmentService;
import com.shuzhuo.core.system.base.AbstractController;
import com.shuzhuo.core.system.sys.service.IOrgService;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shuzhuo.core.common.excel.dto.DeptExcelDTO;
import com.shuzhuo.core.common.excel.dto.DrugExcelDTO;
import com.shuzhuo.core.common.excel.dto.ExcelLogDetailDTO;
import com.shuzhuo.core.common.excel.dto.ImportExcelLogDTO;
import com.shuzhuo.core.common.excel.dto.ItemExcelDTO;
import com.shuzhuo.core.common.excel.dto.MaterialExcelDTO;
import com.shuzhuo.core.common.excel.dto.ScheduleExcelDTO;
import com.shuzhuo.core.common.excel.dto.UserExcelDTO;
import com.shuzhuo.core.common.excel.handler.DeptVerifyHandler;
import com.shuzhuo.core.common.excel.handler.DrugVerifyHandler;
import com.shuzhuo.core.common.excel.handler.ItemVerifyHandler;
import com.shuzhuo.core.common.excel.handler.MaterialVerifyHandler;
import com.shuzhuo.core.common.excel.handler.ScheduleVerifyHandler;
import com.shuzhuo.core.common.excel.handler.UserVerifyHandler;
import com.shuzhuo.core.system.sys.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(tags="excel导入导出类")
@RestController
@RequestMapping("/excel")
public class ExcelController extends AbstractController {

	@Autowired
	private IOrgService orgService;
	@Autowired
	private IUserService userService;
	@Autowired
    private DeptVerifyHandler deptVerifyHandler;
	@Autowired
    private UserVerifyHandler userVerifyHandler;
	@Autowired
    private ItemVerifyHandler itemVerifyHandler;
	@Autowired
    private DrugVerifyHandler drugVerifyHandler;
	@Autowired
    private MaterialVerifyHandler materialVerifyHandler;
	@Autowired
    private ScheduleVerifyHandler scheduleVerifyHandler;
	@Autowired
    private IAttachmentService attachmentService;
	@Autowired
	private ApplicationYmlConfig config;

	@ApiOperation(value = "获取excel模板")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getTemplate")
	public BaseMsg getTemplate(@RequestParam @ApiParam(name = "flag", value = "模板标识：1科室模板，2用户模板，3急救项目，4急救药品，5急救材料，6人员排班", required = true) int flag) {
		String hostPath = config.getResourceHostPort();
		if (flag==1) {
			hostPath = hostPath + config.getExcelDeptTemplate();
		} else if(flag==2){
			hostPath = hostPath + config.getExcelUserTemplate();
		} else if (flag==3) {
			hostPath = hostPath + config.getExcelItemTemplate();
		} else if (flag==4) {
			hostPath = hostPath + config.getExcelDrugTemplate();
		} else if (flag==5) {
			hostPath = hostPath + config.getExcelMaterialTemplate();
		} else if (flag==6) {
			hostPath = hostPath + config.getExcelScheduleTemplate();
		} else {
			return BaseMsg.errorMsg("模板标识不正确");
		}

		return BaseMsg.success().put("excelUrl", hostPath);
	}

	@ApiOperation(value = "导入科室")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importDept")
	public BaseMsg importDept(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(name = "file", value = "科室excel") @RequestParam(value = "file") MultipartFile importFile)throws Exception{
		BaseMsg msg = new BaseMsg();
		if (importFile == null || importFile.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(Constant.IMPORT_EXCEL_TITLE_ROW);
        try {
        	params.setVerifyHanlder(deptVerifyHandler);
            params.setNeedVerfiy(true);
            ExcelImportResult<DeptExcelDTO> importDeptResult = ExcelImportUtil
                    .importExcelVerify(importFile.getInputStream(),DeptExcelDTO.class, params);
            List<DeptExcelDTO> deptExcelList = importDeptResult.getList();

            Workbook workbook = importDeptResult.getWorkbook();

            if (deptExcelList!=null&&deptExcelList.size()>0) {
                ImportExcelLogDTO excelLog = orgService.insertDeptExcel(deptExcelList);
                List<ExcelLogDetailDTO> logDetail = excelLog.getList();
                if (logDetail!=null&&logDetail.size()>0) {
                    wirteImportLog(workbook, logDetail);
                }
                msg.put("msg", "导入" + excelLog.getSuccessCount() + "条数据");
            } else {
            	msg.put("msg","导入0条数据");
            }
            String logId = saveLogFile(request, workbook);

            msg.put("logId",logId);


        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
		return msg;
	}

	@ApiOperation(value = "导入用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importUser")
	public BaseMsg importUser(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(name = "flag", value = "用户标识（1急救人员，2后台用户）", required = true) @RequestParam int flag,
			@ApiParam(name = "file", value = "用户excel") @RequestParam(value = "file") MultipartFile importFile)throws Exception{
		BaseMsg msg = new BaseMsg();
		if (importFile == null || importFile.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		int userType;
		if (flag==1) {
			userType = 3;
		} else if (flag==2) {
			userType = 4;
		} else {
			return BaseMsg.errorMsg("用户标识不正确");
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(Constant.IMPORT_EXCEL_TITLE_ROW);
        try {
        	params.setVerifyHanlder(userVerifyHandler);
            params.setNeedVerfiy(true);
            ExcelImportResult<UserExcelDTO> importUserResult = ExcelImportUtil
                    .importExcelVerify(importFile.getInputStream(),UserExcelDTO.class, params);
            List<UserExcelDTO> userExcelList = importUserResult.getList();

            Workbook workbook = importUserResult.getWorkbook();

            if (userExcelList!=null&&userExcelList.size()>0) {
                ImportExcelLogDTO excelLog = userService.insertUserExcel(userExcelList, userType);
                List<ExcelLogDetailDTO> logDetail = excelLog.getList();
                if (logDetail!=null&&logDetail.size()>0) {
                    wirteImportLog(workbook, logDetail);
                }
                msg.put("msg", "导入" + excelLog.getSuccessCount() + "条数据");
            } else {
            	msg.put("msg","导入0条数据");
            }
            String logId = saveLogFile(request, workbook);

            msg.put("logId",logId);

        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
		return msg;
	}

	@ApiOperation(value = "导入急救项目")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importItem")
	public BaseMsg importItem(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(name = "file", value = "急救项目excel") @RequestParam(value = "file") MultipartFile importFile)throws Exception{
		BaseMsg msg = new BaseMsg();
		if (importFile == null || importFile.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(Constant.IMPORT_EXCEL_TITLE_ROW);
        try {
        	params.setVerifyHanlder(itemVerifyHandler);
            params.setNeedVerfiy(true);
            ExcelImportResult<ItemExcelDTO> importItemResult = ExcelImportUtil
                    .importExcelVerify(importFile.getInputStream(),ItemExcelDTO.class, params);
            List<ItemExcelDTO> itemExcelList = importItemResult.getList();

            Workbook workbook = importItemResult.getWorkbook();

            /*if (itemExcelList!=null&&itemExcelList.size()>0) {
                ImportExcelLogDTO excelLog = itemService.insertItemExcel(itemExcelList);
                List<ExcelLogDetailDTO> logDetail = excelLog.getList();
                if (logDetail!=null&&logDetail.size()>0) {
                    wirteImportLog(workbook, logDetail);
                }
                msg.put("msg", "导入" + excelLog.getSuccessCount() + "条数据");
            } else {
            	msg.put("msg","导入0条数据");
            }
            String logId = saveLogFile(request, workbook);

            msg.put("logId",logId);*/


        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
		return msg;
	}

	@ApiOperation(value = "导入急救药品")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importDrug")
	public BaseMsg importDrug(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(name = "file", value = "急救药品excel") @RequestParam(value = "file") MultipartFile importFile)throws Exception{
		BaseMsg msg = new BaseMsg();
		if (importFile == null || importFile.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(Constant.IMPORT_EXCEL_TITLE_ROW);
        try {
        	params.setVerifyHanlder(drugVerifyHandler);
            params.setNeedVerfiy(true);
            ExcelImportResult<DrugExcelDTO> importDrugResult = ExcelImportUtil
                    .importExcelVerify(importFile.getInputStream(),DrugExcelDTO.class, params);
            List<DrugExcelDTO> drugExcelList = importDrugResult.getList();

            Workbook workbook = importDrugResult.getWorkbook();

           /* if (drugExcelList!=null&&drugExcelList.size()>0) {
                ImportExcelLogDTO excelLog = drugService.insertDrugExcel(drugExcelList);
                List<ExcelLogDetailDTO> logDetail = excelLog.getList();
                if (logDetail!=null&&logDetail.size()>0) {
                    wirteImportLog(workbook, logDetail);
                }
                msg.put("msg", "导入" + excelLog.getSuccessCount() + "条数据");
            } else {
            	msg.put("msg","导入0条数据");
            }*/
            String logId = saveLogFile(request, workbook);

           /* msg.put("logId",logId);*/


        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
		return msg;
	}

	@ApiOperation(value = "导入急救材料")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importMaterial")
	public BaseMsg importMaterial(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(name = "file", value = "急救材料excel") @RequestParam(value = "file") MultipartFile importFile)throws Exception{
		BaseMsg msg = new BaseMsg();
		if (importFile == null || importFile.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(Constant.IMPORT_EXCEL_TITLE_ROW);
        try {
        	params.setVerifyHanlder(materialVerifyHandler);
            params.setNeedVerfiy(true);
            ExcelImportResult<MaterialExcelDTO> importMaterialResult = ExcelImportUtil
                    .importExcelVerify(importFile.getInputStream(),MaterialExcelDTO.class, params);
            List<MaterialExcelDTO> materialExcelList = importMaterialResult.getList();

            Workbook workbook = importMaterialResult.getWorkbook();

            /*if (materialExcelList!=null&&materialExcelList.size()>0) {
                ImportExcelLogDTO excelLog = materialService.insertMaterialExcel(materialExcelList);
                List<ExcelLogDetailDTO> logDetail = excelLog.getList();
                if (logDetail!=null&&logDetail.size()>0) {
                    wirteImportLog(workbook, logDetail);
                }
                msg.put("msg", "导入" + excelLog.getSuccessCount() + "条数据");
            } else {
            	msg.put("msg","导入0条数据");
            }
            String logId = saveLogFile(request, workbook);

            msg.put("logId",logId);*/


        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
		return msg;
	}

	@ApiOperation(value = "导入人员排班")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importSchedule")
	public BaseMsg importSchedule(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(name = "file", value = "人员排班excel") @RequestParam(value = "file") MultipartFile importFile)throws Exception{
		BaseMsg msg = new BaseMsg();
		if (importFile == null || importFile.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(Constant.IMPORT_EXCEL_TITLE_ROW);
        try {
        	params.setVerifyHanlder(scheduleVerifyHandler);
            params.setNeedVerfiy(true);
            ExcelImportResult<ScheduleExcelDTO> importScheduleResult = ExcelImportUtil
                    .importExcelVerify(importFile.getInputStream(),ScheduleExcelDTO.class, params);
            List<ScheduleExcelDTO> scheduleExcelList = importScheduleResult.getList();

            Workbook workbook = importScheduleResult.getWorkbook();

           /* if (scheduleExcelList!=null&&scheduleExcelList.size()>0) {
                ImportExcelLogDTO excelLog = scheduleService.insertScheduleExcel(scheduleExcelList);
                List<ExcelLogDetailDTO> logDetail = excelLog.getList();
                if (logDetail!=null&&logDetail.size()>0) {
                    wirteImportLog(workbook, logDetail);
                }
                msg.put("msg", "导入" + excelLog.getSuccessCount() + "条数据");
            } else {
            	msg.put("msg","导入0条数据");
            }
            String logId = saveLogFile(request, workbook);

            msg.put("logId",logId);*/


        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
		return msg;
	}

    /**
     * 写入导入日志
     *
     * @param workbook
     * @param detailDto
     */
    private void wirteImportLog(Workbook workbook, List<ExcelLogDetailDTO> detailDto) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();

        //错误的样式
        Font errorFont = workbook.createFont();
        errorFont.setFontName("Calibri");
        errorFont.setColor(HSSFColor.RED.index);
        CellStyle errorCellStyle = workbook.createCellStyle();
        errorCellStyle.setFont(errorFont);

        //成功的样式
        Font successFont = workbook.createFont();
        successFont.setFontName("Calibri");
        successFont.setColor(HSSFColor.GREEN.index);
        CellStyle successCellStyle = workbook.createCellStyle();
        successCellStyle.setFont(successFont);

        Integer skipRowNum = Constant.IMPORT_EXCEL_TITLE_ROW + 1;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (skipRowNum > 0) {
                skipRowNum--;
                continue;
            }
            short lastCellNum = row.getLastCellNum();
            Cell cell0 = row.getCell(0);
            if (cell0 != null && cell0.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                Double cell0Value = cell0.getNumericCellValue();
                Cell cell = row.createCell(lastCellNum);

                for (int i = 0; i < detailDto.size(); i++) {
                	ExcelLogDetailDTO logDetailDTO = detailDto.get(i);
                    int num = logDetailDTO.getNum();

                    if (cell0Value.intValue() == num) {
                        if (logDetailDTO.isSuccess()) {
                            cell.setCellStyle(successCellStyle);
                            cell.setCellValue("导入成功");
                        } else {
                            cell.setCellStyle(errorCellStyle);
                            cell.setCellValue(logDetailDTO.getMessage());
                            detailDto.remove(i);
                            i--;
                        }
                        break;
                    }
                }
            }

        }
    }

    /**
     * 保存导入日志文件
     *
     * @param request
     * @param workbook
     * @return
     * @throws IOException
     */
    private String saveLogFile(HttpServletRequest request, Workbook workbook) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] bytes = baos.toByteArray();

        MultipartFile multi = new MyMockMultipartFile("导入日志","导入日志.xlsx",null, bytes);

        BaseMsg msg = attachmentService.saveFile(multi);
        //附件
        Attachment attachment = (Attachment)msg.get("data");

        baos.close();
        return attachment.getId().toString();
    }

}
