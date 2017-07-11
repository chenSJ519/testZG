package com.zg.controller;


import com.zg.config.MySpringConfig;
import com.zg.entity.Content;
import com.zg.entity.InfoXML;
import com.zg.entity.Program;
import com.zg.entity.SimpleData;
import com.zg.mapper.ProgramMapper;
import com.zg.service.ZgService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
@Controller
@RequestMapping("/zg")
public class TestController {
    @Autowired
    private MySpringConfig mySpringConfig;//
    @Autowired
    private ZgService zs;//
    @Autowired
    private ProgramMapper pm;
    @RequestMapping("/index")
    @ResponseBody
    public String index(String signature,String timestamp,String nonce,String echostr)throws Exception
    {

     return echostr;
    }
    @RequestMapping("/programTable")
    @ResponseBody
    public Object indexTable()
    {
        File file=new File(mySpringConfig.getRootPath());
        String[] ss=file.list();
        System.out.println(ss+"哈哈");
        List<Map<String,Object>> listAllProgram=pm.selectAllProgram();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy MM:dd:hh:ss");
        for (Map<String, Object> stringObjectMap : listAllProgram)
        {
            for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
                String name=stringObjectEntry.getKey();
                if("name".equals(name))
                {
                   String taskid= (String) stringObjectMap.get("taskid");
                    stringObjectEntry.setValue("<a href='javascript:;' onclick=\"modaltable('"+taskid+"')\">"+stringObjectEntry.getValue()+"</a>");
                }
                if("scantime".equals(stringObjectEntry.getKey()))
                {
                    stringObjectEntry.setValue(sdf.format(stringObjectEntry.getValue()));
                }
            }

        }

        return listAllProgram;
    }
    @RequestMapping("/mulu")
    @ResponseBody
    public Object mulu()
    {
        File file=new File(mySpringConfig.getRootPath());
        List<SimpleData> list=new ArrayList<SimpleData>();
        fullData(file,list,0,1);
        int i=3;
        return list;
    }
    @RequestMapping("/content")
    public String getContentByTaskid(String taskid,Model model)
    {
        List<Map<String,Object>> contentList=zs.getContentByTaskid(taskid);
        model.addAttribute("contentList",contentList);
        return "content";
    }
    @RequestMapping("/playurl/query")
    @ResponseBody
    public Object playurl(@RequestParam(value="channel[]") String[] channels, String taskid)
    {
        Program program=null;
        for (String channel : channels)
        {
            program=new Program();
            program.setScantime(new Date());
            String videosUrl=channel;

            //解析xml得到视频名字
            InfoXML infoXML=parseInfoXml(videosUrl);
            String titleName=infoXML.getTitleName();
            program.setTaskid(DigestUtils.md5Hex(videosUrl));
            program.setName(titleName);
            List<Content> contentList=new ArrayList<Content>();
            program.setListContent(contentList);
            if(!infoXML.isParseSuccess())
            {
                program.setXmlplan("未完成");
                program.setVideoplan("未完成");
                //TODO
                zs.saveOrUpdate(program);
                zs.delteContentList(program);
                return "success";

            }else
            {
                program.setXmlplan("已完成");
                program.setType(infoXML.getType());
            }
            //创建media
           boolean flag= creatMedia(titleName,videosUrl,program);
            if(flag)
            {
                program.setVideoplan("已完成");
            }else
            {
                program.setVideoplan("未完成");
            }
            zs.saveOrUpdate(program);
            zs.delteContentList(program);

            zs.saveContentList(program.getListContent());




            //创建smil
            writeSmil(videosUrl,titleName);


        }

        int i=3;
        return "success";
    }
    //创建Media
    public boolean creatMedia(String titleName, String videosUrl, Program program)
    {
        boolean flag=true;
        List<Content> contentList=program.getListContent();
        try {
            String mediaPath=mySpringConfig.getMediaPath()+titleName;
            File mediaFile=new File(mediaPath);
            if(!mediaFile.exists())
            {
                mediaFile.mkdir();
            }
           File[] videos= new File(mySpringConfig.getRootPath()+videosUrl+"/videos").listFiles();
            Content content=null;
            for (File video : videos)
            {

                content=new Content();
                content.setPlan("已完成");

                try {
                    FileUtils.copyDirectoryToDirectory(video,new File(mySpringConfig.getMediaPath()+titleName+"/media"));
                } catch (IOException e) {
                    content.setPlan("未完成");
                    e.printStackTrace();
                }
                content.setTaskid(DigestUtils.md5Hex(titleName+video.getName()));

                content.setProgram(program);
                content.setName(video.getName()+titleName);
                content.setUrl(mySpringConfig.getMediaPath()+titleName+"/media/"+titleName+"/"+video.getName());
                contentList.add(content);
            }
            FileUtils.copyDirectoryToDirectory(new File(mySpringConfig.getRootPath()+videosUrl+"/images"),new File(mySpringConfig.getMediaPath()+titleName));
            FileUtils.copyFile(new File(mySpringConfig.getRootPath()+videosUrl+"/"+mySpringConfig.getVideoInfoXML()),new File(mySpringConfig.getMediaPath()+titleName+"/metadata.xml"));



        } catch (Exception e) {
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }
    //解析xml文件
    public InfoXML parseInfoXml(String videosUrl)
    {
        InfoXML infoXML=new InfoXML();
        infoXML.setParseSuccess(true);
        String titleName="";
        try {
            Document doc=new SAXReader().read(new File(mySpringConfig.getRootPath()+videosUrl+"/"+mySpringConfig.getVideoInfoXML()));
            String xpathTitle="/media/title/zh";
            String xpathType="/media/type";
            Element eleTitle=(Element)doc.selectSingleNode(xpathTitle);
            Element eleType=(Element)doc.selectSingleNode(xpathType);
            titleName=eleTitle.getText();
            infoXML.setTitleName(titleName);
            infoXML.setType(eleType.getText());
            System.out.println(eleTitle.getText());
        } catch (Exception e) {
            infoXML.setParseSuccess(false);
            e.printStackTrace();
        }
        return infoXML;
    }
    //创建smil
    public void writeSmil(String videosUrl,String titleName)
    {


        List<List<String>> listUrls=new ArrayList<List<String>>();
        String rootPath=mySpringConfig.getMediaPath();
        File file=new File(rootPath+titleName+"/media");
        File[] fileVideos=file.listFiles();
        for (File fileOneVideo : fileVideos)
        {
            List<String> urls=new ArrayList<String>();//不同清晰度的url集合;
            File[] fileVideo=new File(fileOneVideo.getAbsolutePath()+"/video").listFiles();
            for (File f:fileVideo)
            {
                String s=f.getName();
                s=s.substring(s.lastIndexOf(".")+1);
                if("ts".equals(s))
                {
                    urls.add(f.getAbsolutePath());
                }
            }
            listUrls.add(urls);
        }


        //生成smil文件
        Element root = DocumentHelper.createElement("Person");
        Document document = DocumentHelper.createDocument(root);
        Element smil=root.addElement("smil");
        for (List<String> listUrl : listUrls)
        {
            Element videoEle= smil.addElement("video");
            for(String s:listUrl)
            {
                videoEle.addElement("param") .addAttribute("src",s);

            }
        }


        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");//设置编码格式

        try {
            File smilXml = new File(mySpringConfig.getMediaPath()+titleName+"/smil");
            String smilName= DigestUtils.md5Hex(titleName).toUpperCase();
            smilXml.mkdirs();
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(mySpringConfig.getMediaPath()+titleName+"/smil/"+smilName+".smil"),format);

            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int fullData(File root , List<SimpleData> breach, int pid, int id) {
        int rootLen=10;
        File[] fileList = root.listFiles();
        int len = fileList.length;
        int fatherpid = pid;
        int sonid = id;
        String path = "";
        for (int i = 0; i < len; i++) {
            if (fileList[i].isDirectory()) {
                //List<SimpleData>  breach = new ArrayList<SimpleData> ();
                SimpleData bsd = new SimpleData();
                if(pid==0){
                    bsd.setNocheck(false);
                }
                bsd.setIsParent(true);
                bsd.setpId(fatherpid);
                bsd.setId(sonid);
                bsd.setName(fileList[i].getName());
                bsd.setIsParent(true);
                //
                path = fileList[i].getAbsolutePath().substring(rootLen);
                String path1 = path.replace("\\", "/");
                bsd.setPath(path1);
                breach.add(bsd);
                sonid = fullData(fileList[i], breach, sonid, sonid + 1);
            } else {
                SimpleData leaf = new SimpleData();
                leaf.setpId(fatherpid);
                leaf.setId(sonid);
                leaf.setName(fileList[i].getName());
                path = fileList[i].getAbsolutePath().substring(rootLen);
                String path1 = path.replace("\\", "/");
                leaf.setPath(path1);
                breach.add(leaf);
                sonid++;
            }
        }
        return sonid;
    }

    public static void main(String[] args) {
        try {
           FileUtils.copyDirectoryToDirectory(new File("E:\\zg\\20170314"),new File("E:\\zgMedia\\20170314"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
