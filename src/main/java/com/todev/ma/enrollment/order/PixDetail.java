package com.todev.ma.enrollment.order;

import com.todev.ma.shared.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pix_details")
public class PixDetail extends Model {
    @OneToOne
    private Order order;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String copyAndPasteCode;

    @Column(nullable = false)
    private String qrCodeUrl;

    public PixDetail() {
    }

    public PixDetail(Order order, String copyAndPasteCode, String qrCodeUrl) {
        this.order = order;
        this.copyAndPasteCode = copyAndPasteCode;
        this.qrCodeUrl = qrCodeUrl;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCopyAndPasteCode() {
        return copyAndPasteCode;
    }

    public void setCopyAndPasteCode(String copyAndPasteCode) {
        this.copyAndPasteCode = copyAndPasteCode;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
