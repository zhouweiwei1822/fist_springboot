package com.zhouww.first.springboot.UtilsDemo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zhouww.first.springboot.CommonUtils.PdfUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfDemo {
    public static void main(String[] agr0) throws IOException, DocumentException {
        // 创建 pdf文档器
        Document document = new Document();
        // 将信息写入到文档信息中
        PdfWriter.getInstance(document, new FileOutputStream(new File("D:\\POReceiveReport.pdf")));
        // 写入流中
        // PdfWriter.getInstance(document, out);
        // 打开文档编辑
        document.open();
        //设置pdf纸张格式A4
        document.setPageSize(PageSize.A4);
        // 设置文档标题
        document.addTitle("确认分润函");
        // 设置字体格式 解决中文不显示
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        // 设置字体样式
        Font fp=new Font(baseFont);
        fp.setColor(BaseColor.BLACK);
        fp.setSize(32);
        fp.setStyle(Font.BOLD);
        // 设置第一行文件信息内容
        Paragraph p=new Paragraph("确认分润函",fp);
        // 设置文字信息居中 1是居中 2是靠右对齐 3是靠左对齐
        p.setAlignment(1);
        // 添加到文档中将 段落内容
        document.add(p);
        // 换行设置
        document.add(new Chunk("\n"));
        // 设置字体样式
        Font fCommon=new Font(baseFont);
        fCommon.setColor(BaseColor.RED);
        fCommon.setSize(12);
        fCommon.setStyle(Font.NORMAL);
        // 设置段落内容
        Paragraph pBrhName=new Paragraph("(填写XXX)",fCommon);
        // 将段落添加到文档中
        document.add(pBrhName);
        //document.add(new Chunk("\n"));
        Font fCommon1=new Font(baseFont);
        fCommon1.setColor(BaseColor.BLACK);
        fCommon1.setSize(12);
        fCommon1.setStyle(Font.NORMAL);
        Paragraph pDesc=new Paragraph("XXX情况如下:",fCommon1);
        document.add(pDesc);
        document.add(new Chunk("\n"));

        PdfPTable tble=new PdfPTable(3);
        float[] f={300,300,300};
        tble.setTotalWidth(f);
        // 设置靠左对齐 1是居中 2是靠右对齐 3是靠左对齐
        tble.setHorizontalAlignment(3);
        PdfPCell pdfPCell=null;
        Font f8=new Font(baseFont);
        f8.setColor(BaseColor.BLACK);
        f8.setStyle(Font.NORMAL);
        // 内容标题
        pdfPCell=new PdfPCell(new Paragraph("XXX类型",f8));
        pdfPCell.setHorizontalAlignment(1);
        tble.addCell(pdfPCell);
        pdfPCell=new PdfPCell(new Paragraph("XXX金额",f8));
        pdfPCell.setHorizontalAlignment(1);
        tble.addCell(pdfPCell);
        pdfPCell=new PdfPCell(new Paragraph("XXX金额",f8));
        pdfPCell.setHorizontalAlignment(3);
        tble.addCell(pdfPCell);
        // 退单金额统计
        String orderRetunTotal="";
        // 线下POS收单 交易金额设置
        String payTotal="";


        // 详细内容1
        PdfUtils. setCellInfo( pdfPCell, tble, f8, "XXX收单","444","444");
        // 详细内容
        PdfUtils. setCellInfo( pdfPCell, tble, f8, "XXX退单",orderRetunTotal,"-"+orderRetunTotal);
        //添加空行
        for(int i=0;i<5;i++){
            PdfUtils. setCellInfo( pdfPCell, tble, f8, " "," "," ");
        }
        // 添加合计内容
        PdfUtils.setCellInfo( pdfPCell, tble, f8, "XXX合计","444","444");

        // 添加表格边框为空白
        PdfUtils. setCellINoBorder(pdfPCell, tble, f8, " ", " ", " ");
        PdfUtils. setCellINoBorder(pdfPCell, tble, f8, "XXX信息", " ", " ");
        PdfUtils.setCellINoBorder(pdfPCell, tble, f8, "XXX户名：", " ", " ");
        PdfUtils. setCellINoBorder(pdfPCell, tble, f8, "XXX账号：", " ", " ");
        PdfUtils. setCellINoBorder(pdfPCell, tble, f8, "XXX行：", " ", " ");
        PdfUtils. setCellINoBorder(pdfPCell, tble, f8, " ", " ", " ");
        document.add(tble);
        // 添加描述
        PdfPTable table1=new PdfPTable(1);
        PdfPCell contentCell=null;
        PdfUtils.setSingleCellINoBorderRight( contentCell,table1 , fCommon1,"XXX对相关数据信息核实，并开具增值税专用发票");
        document.add(table1);

        PdfPTable table2=new PdfPTable(3);
        //设置单元格分布比例
        float[] table2With={300,150,150};
        table2.setTotalWidth(table2With);
        table2.setHorizontalAlignment(3);
        PdfPCell contentCell1=null;
        PdfUtils.setCellInfo(contentCell1,table2,fCommon1,3,2,3,"XXX增值税专用发票金额：", "444","元");
        document.add(table2);


        PdfPTable table3=new PdfPTable(1);
        PdfPCell contentCell3=null;
        // 添加一个空白行
        PdfUtils.setSingleCellINoBorderRight( contentCell3,table3 , fCommon1," ");
        // 添加内容
        PdfUtils. setSingleCellINoBorderRight( contentCell3,table3 , fCommon1,"XXX，XXX，XXXXXXXXXXXXX，XXXXXXXXXXXXX，谢谢！");
        PdfUtils. setSingleCellINoBorderRight( contentCell3,table3 , fCommon1,"XXX信息如下：");
        PdfUtils.setSingleCellINoBorderRight( contentCell3,table3 , fCommon1,"   XXXXXXXXXXXXXXXXXXXXXXXXXX。");
        PdfUtils.setSingleCellINoBorderRight( contentCell3,table3 , fCommon1,"XXXXXXXXXXXXXXXXXXXXXXXXXX。XXXXXXXXXXXXXXXXXXXXXXXXXX。");
        document.add(table3);


        PdfPTable table4=new PdfPTable(3);
        //设置单元格分布比例
        float[] table4With={300,300,300};
        table4.setTotalWidth(table4With);
        table4.setHorizontalAlignment(3);
        PdfPCell contentCell4=null;
        PdfUtils.setCellInfo(contentCell4,table4,fCommon1,3,2,3," ", "XXX签章："," ");

        PdfUtils.setCellInfo(contentCell4,table4,fCommon1,3,2,3," ", "XX日期："," ");

        document.add(table4);
        document.close();
    }

}
