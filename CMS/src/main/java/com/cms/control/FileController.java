package com.cms.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cms.util.CMSException;
import com.cms.util.CookieUtil;
import com.cms.util.POIUtil;


@Controller
@RequestMapping("/file")
public class FileController {



	
	/*** 
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile 
     *  
     * @param file 
     * @return 
     */
	//获取‘资质上传’页面
	@RequestMapping(value = "/getUploadPage.do")
	public ModelAndView getUploadPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelMap map = new ModelMap();
		try{
			Cookie[] cookies = request.getCookies();
			String userRole = CookieUtil.getUserRole(cookies);
			if(userRole.equals("worker")) {
				return new ModelAndView("uploadWorker", null);
			}else {
				return new ModelAndView("uploadFile", null);
			}	
		}catch (Exception e) {
			e.printStackTrace();
			if(e.getClass()==CMSException.class){
				map.addAttribute("msg", e.getMessage());
				return new ModelAndView("error", map);
			}
		}
		return new ModelAndView("error", null);
	}
		
    @RequestMapping("/fileUpload.do")  
    public String fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request ) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {  
                // 文件保存路径  
                String filePath = request.getSession().getServletContext().getRealPath("/")   
                        + file.getOriginalFilename();  
                // 转存文件  
               file.transferTo(new File(filePath));  
              List<String[]> list= POIUtil.readExcel(file); 
              System.out.println(list);
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        // 重定向  
        return "redirect:/menu.jsp";  
    }  
  
    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("/Upload.do")
    public String  springUpload(HttpServletRequest request) throws Exception
    {
         long  startTime=System.currentTimeMillis();
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
             
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	Cookie[] cookies = request.getCookies();
                	int userId = CookieUtil.getUserId(cookies);
                	String idString = Integer.toString(userId);
                    String filePath = request.getSession().getServletContext().getRealPath("/")+"certify/"
                    		+ idString + ".zip";
                    file.transferTo(new File(filePath));
                }
                 
            }
           
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
    return "/menu"; 
    }
    
    @RequestMapping("/Download.do")
    public ResponseEntity<byte[]> download(HttpServletRequest request,HttpServletResponse response)  {
    	
        try {
        	String idString=request.getParameter("userId");
            File file = new File(request.getSession().getServletContext().getRealPath("/")+"certify/"
            		+ idString + ".zip");
            byte[] body = null;
            InputStream is = new FileInputStream(file);
			body = new byte[is.available()];
			is.read(body);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
	        HttpStatus statusCode = HttpStatus.OK;
	        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
	        return entity;
		} catch (IOException e) {
			e.printStackTrace();
			HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(statusCode);
			return entity;
		}
		
        
    }
    /*** 
     * 读取上传文件中得所有文件并返回 
     *  
     * @return 
     */  
    /*@RequestMapping("list")  
    public ModelAndView list() {  
        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";  
        ModelAndView mav = new ModelAndView("list");  
        File uploadDest = new File(filePath);  
        String[] fileNames = uploadDest.list();  
        for (int i = 0; i < fileNames.length; i++) {  
            //打印出文件名  
            System.out.println(fileNames[i]);  
        }  
        return mav;  
    }  */
}
