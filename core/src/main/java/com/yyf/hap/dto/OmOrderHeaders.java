package com.yyf.hap.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "hap_om_order_headers")
public class OmOrderHeaders extends BaseDTO {

     public static final String FIELD_HEADER_ID = "headerId";
     public static final String FIELD_ORDER_NUMBER = "orderNumber";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ORDER_DATE = "orderDate";
     public static final String FIELD_ORDER_STATUS = "orderStatus";
     public static final String FIELD_CUSTOMER_ID = "customerId";


     @Id
     @GeneratedValue
     private Long headerId;

     @NotEmpty
     @Length(max = 60)
     private String orderNumber;

     @NotNull
     private Long companyId;

     private Date orderDate;

     @NotEmpty
     @Length(max = 30)
     private String orderStatus;

     @NotNull
     private Long customerId;

    @Transient
    private String customerName;

    @Transient
    private String companyName;

    @Transient
    private BigDecimal account_money;

    @Transient
    private Long inventoryItemId;

    @Children
    @Transient
    private List<OmOrderLines> omOrderLines;

    public List<OmOrderLines> getOmOrderLines() {
        return omOrderLines;
    }

    public void setOmOrderLines(List<OmOrderLines> omOrderLines) {
        this.omOrderLines = omOrderLines;
    }

    public Long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(Long inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public BigDecimal getAccount_money() {
        return account_money;
    }

    public void setAccount_money(BigDecimal account_money) {
        this.account_money = account_money;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setHeaderId(Long headerId){
         this.headerId = headerId;
     }

     public Long getHeaderId(){
         return headerId;
     }

     public void setOrderNumber(String orderNumber){
         this.orderNumber = orderNumber;
     }

     public String getOrderNumber(){
         return orderNumber;
     }

     public void setCompanyId(Long companyId){
         this.companyId = companyId;
     }

     public Long getCompanyId(){
         return companyId;
     }

     public void setOrderDate(Date orderDate){
         this.orderDate = orderDate;
     }

     public Date getOrderDate(){
         return orderDate;
     }

     public void setOrderStatus(String orderStatus){
         this.orderStatus = orderStatus;
     }

     public String getOrderStatus(){
         return orderStatus;
     }

     public void setCustomerId(Long customerId){
         this.customerId = customerId;
     }

     public Long getCustomerId(){
         return customerId;
     }

     }
