package com.yyf.hap.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.FileOutputStream;


public class PDFReport {
    Document document = new Document();// 建立一个Document对象

    public static Font headfont ;// 设置字体大小
    public static Font keyfont;// 设置字体大小
    public static Font textfont;// 设置字体大小


    static{
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("C:/windows/fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            headfont = new Font(bfChinese, 18, Font.BOLD,BaseColor.BLACK);// 设置字体大小
            keyfont = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小
            textfont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public PDFReport(File file) {
        document.setPageSize(PageSize.A4);// 设置页面大小
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public PDFReport(){

    }
    int maxWidth = 520;


    public PdfPCell createCell(String value,Font font,int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    public PdfPCell createCell(String value,Font font){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    public PdfPCell createCell(String value,Font font,int align,int colspan){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }
    public PdfPCell createCell(String value,Font font,int align,int colspan,boolean boderFlag){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value,font));
        cell.setPadding(3.0f);
        if(!boderFlag){
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }
    public PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);
        try{
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }
    public PdfPTable createTable(float[] widths){
        PdfPTable table = new PdfPTable(widths);
        try{
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }

    public PdfPTable createBlankTable(){
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }

}

