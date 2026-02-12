-- Production-style PostgreSQL example for equivalent stored procedure behavior.
-- Use this script in real database environments instead of H2 alias setup.

CREATE TABLE IF NOT EXISTS rebate_application (
    application_id BIGSERIAL PRIMARY KEY,
    vendor_id VARCHAR(30) NOT NULL,
    vendor_name VARCHAR(100) NOT NULL,
    product_code VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    unit_price NUMERIC(18,2) NOT NULL,
    rebate_percentage NUMERIC(5,2) NOT NULL,
    remarks VARCHAR(255),
    rebate_amount NUMERIC(18,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION sp_submit_rebate_application(
    p_vendor_id VARCHAR,
    p_vendor_name VARCHAR,
    p_product_code VARCHAR,
    p_quantity INT,
    p_unit_price NUMERIC,
    p_rebate_percentage NUMERIC,
    p_remarks VARCHAR
)
RETURNS TABLE (
    o_application_id BIGINT,
    o_rebate_amount NUMERIC,
    o_status_code VARCHAR,
    o_status_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_rebate_amount NUMERIC(18,2);
BEGIN
    v_rebate_amount := (p_quantity * p_unit_price) * (p_rebate_percentage / 100);

    INSERT INTO rebate_application (
        vendor_id,
        vendor_name,
        product_code,
        quantity,
        unit_price,
        rebate_percentage,
        remarks,
        rebate_amount
    ) VALUES (
        p_vendor_id,
        p_vendor_name,
        p_product_code,
        p_quantity,
        p_unit_price,
        p_rebate_percentage,
        p_remarks,
        v_rebate_amount
    ) RETURNING application_id INTO o_application_id;

    o_rebate_amount := v_rebate_amount;
    o_status_code := 'SUCCESS';
    o_status_message := 'Rebate application submitted successfully';

    RETURN NEXT;
END;
$$;
