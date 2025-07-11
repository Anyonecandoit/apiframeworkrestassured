package sa.com.kranthi.e2eAutomation.backend.foundation.mysqlConfigs;

public class DBQueries {

    // From image 1 (top to bottom) & image 4 (bottom)
    public static final String UPDATE_AGENT_TXN_DATA_AGENT = "update remittance_baraga.agent_transaction_limits set value = ? " +
            "where limit_description = 'AGENT_TRANSACTION_PER_DAY_FREQUENCY'"; // 1 usage
    public static final String UPDATE_COMPLIANCE_AGENT_LIMIT = "update remittance_baraga.compliance_limit set value = ? " +
            "where limit_description = 'AGENT_TRANSACTION_PER_DAY_FREQUENCY'"; // 1 usage
    public static final String TRANSACTION_PRICE_WHERE_ID = "select transaction_price from remittance_baraga.transaction " +
            "where reference_id = ?"; // 1 usage
    public static final String UPDATE_CB_LIMIT = "update core_wallet_db.customer_wallet_limit set data_limit_max = ? " +
            "where account_no = ?"; // 1 usage
    public static final String GET_RM_TXN_TO_TXN_DATE = "select * from remittance_baraga.transaction where reference_id = ?"; // 1 usage
    public static final String UPDATE_TP_COMP_LIMITS = "update remittance_baraga.compliance_limit set value = '90000000'"; // 1 usage
    public static final String UPDATE_SENDER_DETAILS_FOR_SENDER_HOLD = "update customer_identity_db.users set " +
            "birth_date = '1980-01-01', first_name = 'Larry', second_name = '', third_name = '', " +
            "last_name = '' where cif = ?"; // 1 usage
    public static final String RESET_SENDER_DETAILS = "update customer_identity_db.users SET " +
            "birth_date = '2001-01-01', first_name = 'ABDULLAH', second_name = '', 'ABDULLAZIZ', third_name = 'ABDULLA', " +
            "last_name = '' WHERE cif = ?"; // 1 usage
    public static final String UPDATE_TXN_STATUS_FOR_HTS_HOLD = "update transaction set src_transaction_status = ?, " +
            "rm_status = ? where reference_id = ?"; // 1 usage

    // From image 2 (middle to bottom)
    public static final String CHECK_PHONE_NUMBER_EXISTS_G1 = "select * from customer_identity_db.users where phone_no = ?"; // 1 usage
    public static final String CHECK_PHONE_NUMBER_EXISTS_AUTH = "select * from auth_service_db.users where phone_no = ?"; // no usage
    public static final String CHECK_TD_ID_EXISTS = "select * from customer_identity_db.users where id_no = ?"; // 1 usage
    public static final String GET_CUSTOMER_DETAILS = "SELECT * FROM core_wallet_db.customer_wallet where account_no = ?"; // 1 usage
    public static final String GET_WALLET_LEDGER_DETAILS = "SELECT * FROM core_wallet_db.customer_wallet_ledger where txn_category_txn_id = ?"; // 1 usage
    public static final String GET_ACCOUNT_SETTLEMENT = "SELECT * FROM core_accounting_db.accounting_settlement where txn_category_txn_id = ?"; // 1 usage
    public static final String GET_WALLET_FROZEN_SETTLEMENT = "SELECT sum(txn_amount) as overall_txn_amount from core_wallet_db.customer_wallet_ledger " +
            "where txn_category_txn_id=?"; // 1 usage
    public static final String GET_BENEFICIARY_DETAILS = "SELECT * from core_payments_db.cashout_beneficiaries where is_active = 1 AND cif = ?"; // 2 usages
    public static final String GET_CASHOUT_TRANSACTION = "SELECT * from core_payments_db.cashout_transactions WHERE id = ?"; // 1 usage
    public static final String GET_PHONE_NO = "SELECT * from auth_service_db.users where phone_no = ?"; // 1 usage
    public static final String GET_TENANT_CODE = "SELECT * from auth_service_db.users where user_id = ?"; // 1 usage
    public static final String GET_DEVICE_BLACKLIST_DATA = "select * from customer_identity_db.blacklisted_devices where device_id = ?"; // 2 usages
    public static final String GET_PHONE_BLACKLIST_DATA = "select * from customer_identity_db.blacklisted_phones where phone_no = ?"; // 2 usages
    public static final String GET_TD_ID_BLACKLIST_DATA = "select * from customer_identity_db.blacklisted_ids where td_id = ?"; // 1 usage
    public static final String GET_APPLICATION_CHECKS_FOR_SUCCESSFUL_PHARMA = "select * from customer_identity_db.user_application_checks where user_id = ? " +
            "and check_type = ? and created_at is null "; // 2 usages
    public static final String GET_CTP_STATUS = "select cif_mapper_status from customer_identity_db.users where user_id = ?"; // 1 usage
    public static final String GET_TD_AND_PHONE_NUMBER = "select phone_no, td_no from customer_identity_db.users where user_id = ?"; // 1 usage
    public static final String CHECK_TENANT_CODE_COLUMN = "SELECT COUNT(*) as tenant_value from information_schema.columns where table_name = 'users' AND " +
            "column_name = 'tenant_code' AND table_schema = 'auth_service_db'"; // 1 usage

    // From image 3 (top to bottom) & image 4 (top)
    public static final String UPDATE_BALANCE = "update core_wallet_db.customer_wallet set balance = ? where account_no = ?"; // 2 usages
    public static final String GET_USER_DATA_FROM_PHONE_NO = "select us.phone_no,us.user_id,us.cif_id,us.td_no,us.cif_super_status,us.account_no " +
            "from customer_identity_db.users us join customer_identity_db.users_accounts ua on us.user_id = ua.user_id " +
            "join core_wallet_db.customer_wallet cw on cw.account_no = ua.account_no where us.phone_no = ?"; // 1 usage
    public static final String GET_JOURNEY_ID = "select journey_id from customer_identity_db.pre_signed_user_journey where user_id = ?"; // 1 usage
    public static final String UPDATE_DA_LIMITS = "update core_wallet_db.customer_wallet_limit set data_limit_used = ?, data_limit_max = ? " +
            "WHERE account_no = ?"; // 1 usage
    public static final String UPDATE_GLOBAL_LIMITS = "update remittance_baraga.global_limits set transaction_value = ? where bcc = ?"; // 1 usage
    public static final String UPDATE_USER_AND_CREATE_LIMIT = "update remittance_baraga.user_beneficiary_create_limits set value = ? where "; // 1 usage (incomplete, 'where' clause cut off)
    public static final String UPDATE_BENEFICIARY_LIMITS = "update remittance_baraga.beneficiary_transaction_limits set value = ? where "; // 1 usage (incomplete, 'where' clause cut off)
    public static final String UPDATE_TD_TO_MG = "update remittance_baraga.provider set is_active = ? where name = 'MoneyGram'"; // 1 usage
    public static final String UPDATE_TD_TO_WU = "update remittance_baraga.provider set is_active = ? where name = 'Western Union'"; // 1 usage
    public static final String GET_PROVIDER = "select name, is_active from remittance_baraga.provider"; // no usages
    public static final String UPDATE_PROVIDER = "update remittance_baraga.provider set is_active = ? where name = ?"; // no usages
    public static final String GET_GLOBAL_LIMITS_USER_TRANSACTION = "select cl.limit_description, clut.value from remittance_baraga.compliance_limit cl " +
            "and cl.delivery_option = ? and cl.gut_pif = ?"; // 1 usage
    public static final String GET_USER_BENEFICIARY_CREATE_LIMIT = "select * from remittance_baraga.user_beneficiary_create_limits where user_id = ?"; // 1 usage (based on context, completed)
    public static final String GET_COUNTRY_LIMITS_TRANSACTION = "select cl.limit_description, clt.value from remittance_baraga.country_transaction_limits clt " +
            "join remittance_baraga.compliance_limit cl where cl.id = clt.compliance_limit"; // 1 usage
    public static final String GET_BENEFICIARY_TRANSACTION_LIMIT = "select cl.limit_description, clt.value from remittance_baraga.beneficiary_transaction_limits clt " +
            "join remittance_baraga.compliance_limit cl where cl.id = clt.compliance_limit"; // 1 usage
    public static final String UPDATE_CREATE_BENEFICIARY_LIMIT = "update remittance_baraga.beneficiary_transaction_limits set value = 1000 " +
            "where limit_description = 'BENEFICIARY_CREATE_PER_DAY_FREQUENCY'"; // 1 usage (completed based on surrounding text)
    public static final String GET_CORRIDOR_TD_FROM_WV_ID = "select corridor from remittance_baraga.beneficiary_in where id = ?"; // 1 usage
    public static final String GET_RM_FEE_LIMITS = "select min_limit, max_limit from remittance_baraga.country_receive_details_ag " +
            "where country_receive_details_id = ?"; // 1 usage
    public static final String GET_TP_FEE_LIMITS = "select min_limit, max_limit from remittance_baraga.country_receive_details_tp " +
            "where id = (select country_receive_details_tp from remittance_baraga)"; // 1 usage
    public static final String GET_TD_IN_TXN = "select txn_reference_number from remittance_baraga.transaction where reference_id = ?"; // 1 usage
    public static final String GET_RM_TXN_DETAILS = "select * from remittance_baraga.transaction where reference_id = ?"; // 4 usages
    public static final String GET_RM_TXN_SAD_ID = "select sad_reference_id from remittance_baraga.transaction where reference_id = ?"; // 2 usages
    public static final String GET_RM_TXN_STATUS = "select src_transaction_status, utp_transaction_status, baraga_status, baraga_auto_status, " +
            "frontend_status, compliance_status, crew_booking_status, file_status, sad_status from remittance_baraga.transaction " +
            "where reference_id = ?"; // 3 usages
    public static final String GET_AGENT_TXN_DATA = "select value from remittance_baraga.agent_transaction_limits where pif = ? and beneficiary = ?"; // 1 usage
    public static final String UPDATE_AGENT_TXN_DATA = "update remittance_baraga.agent_transaction_limits set value = ? " +
            "where pif = 0 and beneficiary = ?"; // 1 usage
    public static final String UPDATE_COMPLIANCE_AGENT_LIMIT_2 = "update remittance_baraga.compliance_limit set value = ?"; // 1 usage

}