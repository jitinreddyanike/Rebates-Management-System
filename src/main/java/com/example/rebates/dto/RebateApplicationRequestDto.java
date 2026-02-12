package com.example.rebates.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class RebateApplicationRequestDto {

    @NotBlank(message = "vendorId is required")
    @Size(max = 30, message = "vendorId cannot exceed 30 characters")
    private String vendorId;

    @NotBlank(message = "vendorName is required")
    @Size(max = 100, message = "vendorName cannot exceed 100 characters")
    private String vendorName;

    @NotBlank(message = "productCode is required")
    @Size(max = 20, message = "productCode cannot exceed 20 characters")
    private String productCode;

    @NotNull(message = "quantity is required")
    @Positive(message = "quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "unitPrice is required")
    @DecimalMin(value = "0.01", message = "unitPrice must be at least 0.01")
    private BigDecimal unitPrice;

    @NotNull(message = "rebatePercentage is required")
    @DecimalMin(value = "0.01", message = "rebatePercentage must be at least 0.01")
    private BigDecimal rebatePercentage;

    @Size(max = 255, message = "remarks cannot exceed 255 characters")
    private String remarks;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRebatePercentage() {
        return rebatePercentage;
    }

    public void setRebatePercentage(BigDecimal rebatePercentage) {
        this.rebatePercentage = rebatePercentage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
