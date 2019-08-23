package com.yyf.hap.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@ExtensionAttribute(disable=true)
@Table(name = "hap_om_order_lines")
public class OmOrderLines extends BaseDTO {

     public static final String FIELD_LINE_ID = "lineId";
     public static final String FIELD_HEADER_ID = "headerId";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_INVENTORY_ITEM_ID = "inventoryItemId";
     public static final String FIELD_ORDERD_QUANTITY = "orderdQuantity";
     public static final String FIELD_ORDER_QUANTITY_UOM = "orderQuantityUom";
     public static final String FIELD_UNIT_SELLING_PRICE = "unitSellingPrice";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ADDITION1 = "addition1";
     public static final String FIELD_ADDITION2 = "addition2";
     public static final String FIELD_ADDITION3 = "addition3";
     public static final String FIELD_ADDITION4 = "addition4";
     public static final String FIELD_ADDITION5 = "addition5";


     @Id
     @GeneratedValue
     private Long lineId;


     private Long headerId;


     private Long lineNumber;


     private Long inventoryItemId;

     @NotNull
     private Long orderdQuantity;


     @Length(max = 30)
     private String orderQuantityUom;

     @NotNull
     private BigDecimal unitSellingPrice;

     @NotEmpty
     @Length(max = 240)
     private String description;


     private Long companyId;

     @Length(max = 150)
     private String addition1;

     @Length(max = 150)
     private String addition2;

     @Length(max = 150)
     private String addition3;

     @Length(max = 150)
     private String addition4;

     @Length(max = 150)
     private String addition5;

    @NotEmpty
    @Transient
    private String itemDescription;

    @NotEmpty
    @Transient
    private String itemCode;

    @NotEmpty
    @Transient
    private String itemUom;

    @Transient
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getItemUom() {
        return itemUom;
    }

    public void setItemUom(String itemUom) {
        this.itemUom = itemUom;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setLineId(Long lineId){
         this.lineId = lineId;
     }

     public Long getLineId(){
         return lineId;
     }

     public void setHeaderId(Long headerId){
         this.headerId = headerId;
     }

     public Long getHeaderId(){
         return headerId;
     }

     public void setLineNumber(Long lineNumber){
         this.lineNumber = lineNumber;
     }

     public Long getLineNumber(){
         return lineNumber;
     }

     public void setInventoryItemId(Long inventoryItemId){
         this.inventoryItemId = inventoryItemId;
     }

     public Long getInventoryItemId(){
         return inventoryItemId;
     }

     public void setOrderdQuantity(Long orderdQuantity){
         this.orderdQuantity = orderdQuantity;
     }

     public Long getOrderdQuantity(){
         return orderdQuantity;
     }

     public void setOrderQuantityUom(String orderQuantityUom){
         this.orderQuantityUom = orderQuantityUom;
     }

     public String getOrderQuantityUom(){
         return orderQuantityUom;
     }

     public void setUnitSellingPrice(BigDecimal unitSellingPrice){
         this.unitSellingPrice = unitSellingPrice;
     }

     public BigDecimal getUnitSellingPrice(){
         return unitSellingPrice;
     }

     public void setDescription(String description){
         this.description = description;
     }

     public String getDescription(){
         return description;
     }

     public void setCompanyId(Long companyId){
         this.companyId = companyId;
     }

     public Long getCompanyId(){
         return companyId;
     }

     public void setAddition1(String addition1){
         this.addition1 = addition1;
     }

     public String getAddition1(){
         return addition1;
     }

     public void setAddition2(String addition2){
         this.addition2 = addition2;
     }

     public String getAddition2(){
         return addition2;
     }

     public void setAddition3(String addition3){
         this.addition3 = addition3;
     }

     public String getAddition3(){
         return addition3;
     }

     public void setAddition4(String addition4){
         this.addition4 = addition4;
     }

     public String getAddition4(){
         return addition4;
     }

     public void setAddition5(String addition5){
         this.addition5 = addition5;
     }

     public String getAddition5(){
         return addition5;
     }



     }