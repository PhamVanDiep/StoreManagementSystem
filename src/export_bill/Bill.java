package export_bill;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import main.Controller;

public class Bill {
	
	private static BaseFont baseFont_store_name;
	private static BaseFont baseFont_store_type;
	private static BaseFont baseFont_regular;
	private static BaseFont baseFont_table;
	private static BaseFont baseFont_i;
	private static BaseFont baseFont_b;
	
    private static Font font_name;
    private static Font font_regular;
    private static Font font_type;
    private static Font font_table;
    private static Font font_head;
    private static Font font_i;
    private static Font font_b;
    
    private boolean isDailyPayment;
    
    private JTable table;
    
    private float[] floats;
    
    private int total_price;
    private int paid;
    
    private String customer_name;
    private String customer_phone_number;
    private String customer_address;
    
    private int k;
	public Bill(boolean isDailyPayment, JTable table,int k, float[] floats, int totalPrice, int paid, 
			String customer_name, String customer_phone_number, String customer_address) throws Exception{
		this.isDailyPayment = isDailyPayment;
		this.table = table;
		this.k = k;
		this.floats = floats;
		this.total_price = totalPrice;
		this.paid = paid;
		
		this.customer_name = customer_name;
		this.customer_phone_number = customer_phone_number;
		this.customer_address = customer_address;
		
		initFont();
		Document document = new Document();
        String userDir = System.getProperty("user.home");
        JFileChooser jFileChooser = new JFileChooser(userDir +"/Desktop");
        jFileChooser.setDialogTitle("Save a file");
        jFileChooser.setFileFilter(new FileTypeFilter(".pdf", "Pdf File"));
        int result = jFileChooser.showSaveDialog(null);
        
        String file_path = null;
        if (result == JFileChooser.APPROVE_OPTION) {
     	   File file = jFileChooser.getSelectedFile();
     	   file_path = file.getPath();
        }
        if (file_path != null) {
     	   PdfWriter.getInstance(document, new FileOutputStream(file_path+".pdf"));
        }
        
        document.open();
        addStoreInfo(document);
        addBillInfo(document);
        addTable(document);
        addConfirmForm(document);
        document.close();
	}

	private void addConfirmForm(Document document) throws DocumentException {
		Paragraph confirmForm = new Paragraph();
		Paragraph dateParagraph;
		
		if (isDailyPayment) {
			String date = new SimpleDateFormat("dd").format(new Date());
			String month = (new SimpleDateFormat("MM")).format(new Date());
			String year = (new SimpleDateFormat("yyyy")).format(new Date());
			
			dateParagraph = new Paragraph("                                             , Ngày " + date + " tháng "+ month + " năm " + year,font_regular);
			dateParagraph.setAlignment(Element.ALIGN_RIGHT);
			
		}else {
			
			dateParagraph = new Paragraph("                                             , Ngày " + "     " + " tháng "+ "     " + " năm " + "     ",font_regular);
			dateParagraph.setAlignment(Element.ALIGN_RIGHT);
			
		}
		
		confirmForm.add(dateParagraph);
		
		Paragraph title = new Paragraph("                            Khách hàng                                                           Thay mặt cửa hàng",font_b);
		confirmForm.add(title);
		
		Paragraph title2 = new Paragraph("                           (Ký, ghi rõ họ tên)                                                               (Ký, ghi rõ họ tên)", font_i);
		confirmForm.add(title2);
		
		document.add(confirmForm);
	}

	private void addTable(Document document) throws DocumentException {
		PdfPTable pdfTable = new PdfPTable(floats);
        pdfTable.setWidthPercentage(100);
        for (int i = 0; i < table.getColumnCount()-k; i++) {
			if (i!=1) {
				PdfPCell pdfPCell = new PdfPCell(new Phrase(table.getColumnName(i), font_table));
				pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pdfTable.addCell(pdfPCell);
			}
        }
        //extracting data from the JTable and inserting it to PdfPTable
        for (int rows = 0; rows < table.getRowCount(); rows++) {
            for (int cols = 0; cols < table.getColumnCount()-k; cols++) {
                if (cols != 1) {
					PdfPCell pdfPCell = new PdfPCell(new Phrase(table.getModel().getValueAt(rows, cols).toString(), font_table));
					if (cols == 0 || cols == 3 || cols == 4) {
						pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					}else if (cols == 6) {
						pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					pdfTable.addCell(pdfPCell);
				}
            }
        }
        
        document.add(pdfTable);
        
        Paragraph totalPrice = new Paragraph("Tổng tiền: " + Controller.priceWithDecimal(total_price) + " VNĐ", font_regular);
        totalPrice.setAlignment(Element.ALIGN_LEFT);
        document.add(totalPrice);
        
        if (isDailyPayment == false) {
        	Paragraph p = new Paragraph("Đã thanh toán: " + Controller.priceWithDecimal(paid) + " VNĐ", font_regular);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            
            Paragraph i = new Paragraph("Còn nợ: " + Controller.priceWithDecimal(total_price - paid) + " VNĐ", font_regular);
            i.setAlignment(Element.ALIGN_LEFT);
            document.add(i);
		}
        addEmptyLine(totalPrice, 1);
        
	}

	private void addBillInfo(Document document) throws DocumentException {
		Paragraph billInfo = new Paragraph();
		
		Paragraph customerName = new Paragraph("Tên khách hàng: " + customer_name, font_regular);
		customerName.setAlignment(Element.ALIGN_LEFT);
		billInfo.add(customerName);
		
		Paragraph customerPhoneNumber = new Paragraph("Số điện thoại: " + customer_phone_number, font_regular);
		customerPhoneNumber.setAlignment(Element.ALIGN_LEFT);
		billInfo.add(customerPhoneNumber);
		
		Paragraph customerAddress = new Paragraph("Địa chỉ: " + customer_address, font_regular);
		customerAddress.setAlignment(Element.ALIGN_LEFT);
		billInfo.add(customerAddress);
		addEmptyLine(billInfo, 1);
		
		document.add(billInfo);
	}
    
	private void addStoreInfo(Document document) throws DocumentException {
		Paragraph storeInfo = new Paragraph();
		
		Paragraph storeName = new Paragraph("Cửa hàng " + Controller.store_name, font_name);
		storeName.setAlignment(Element.ALIGN_CENTER);
		storeInfo.add(storeName);
		
		Paragraph storePhoneNumber = new Paragraph("SĐT: " + Controller.phone_number, font_regular);
		storePhoneNumber.setAlignment(Element.ALIGN_CENTER);
		storeInfo.add(storePhoneNumber);
		
		Paragraph storeAddress = new Paragraph("Địa chỉ: " + Controller.address, font_regular);
		storeAddress.setAlignment(Element.ALIGN_CENTER);
		storeInfo.add(storeAddress);
		
		Paragraph storeType = new Paragraph(Controller.type, font_type);
		storeType.setAlignment(Element.ALIGN_CENTER);
		storeInfo.add(storeType);
		addEmptyLine(storeInfo,2);
		
		Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", font_head);
		title.setAlignment(Element.ALIGN_CENTER);
		storeInfo.add(title);
		addEmptyLine(storeInfo,1);
		document.add(storeInfo);
	}
	
	private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
	private void initFont() throws DocumentException, IOException {
		baseFont_store_name = BaseFont.createFont("font/store_name.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		baseFont_store_type = BaseFont.createFont("font/store-type.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		baseFont_regular = BaseFont.createFont("font/regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		baseFont_table = BaseFont.createFont("font/table.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		baseFont_i = BaseFont.createFont("font/i.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		baseFont_b = BaseFont.createFont("font/b.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		
		font_name = new Font(baseFont_store_name, 18);
		font_regular = new Font(baseFont_regular, 14);
		font_type = new Font(baseFont_store_type, 16);
		font_table = new Font(baseFont_table, 14);
		font_head = new Font(baseFont_regular, 20);
		font_i = new Font(baseFont_i, 13);
		font_b = new Font(baseFont_b, 14);
	}
}
