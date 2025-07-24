package com.delivery.mydelivery.services.documents.autofill;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.entities.organization.OrganizationDetails;
import com.delivery.mydelivery.database.projections.organization.OrganizationDetailsProjection;
import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.ordermanage.OrderPickupService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.organization.OrganizationDetailsService;
import com.delivery.mydelivery.dto.ordermanage.orderpickup.OrderPickupDTO;
import com.delivery.mydelivery.services.metadata.ServerDataService;
import com.delivery.mydelivery.utility.file.FIleUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AgreementFillerService {
    @Autowired
    OrderService orderService;
    @Autowired
    ClientService clientService;
    @Autowired
    OrganizationDetailsService organizationDetailsService;
    @Autowired
    ServerDataService serverDataService;
    //where docs is stored
    @Value("${documents.agreements}")
    private String documentsAgreementsPath;
    @Value("${web.documents.agreements}")
    private String webDocumentsAgreements;
    //where template is stored
    @Value("${documents.templates.agreement}")
    private String agreementTemplate;

    public void createAgreement(OrderPickupDTO orderPickupDTO) throws IOException {
        ClientOrder order = orderService.getClientOrderById(orderPickupDTO.getOrderId());
        OrganizationDetailsProjection organizationDetailsProjection =
                organizationDetailsService.getOrganizationDetailsByOrganizationId(order.getOrganization().getId());
        XWPFDocument agreementDocs =
                DocxFillerUtil.fillDocx(agreementTemplate, extractFieldsData(organizationDetailsProjection, order, orderPickupDTO));
        String fileName = FIleUtil.generateUniqueFilename("agreement.docx");
        String fullPath = documentsAgreementsPath + fileName;
        String webUrl = webDocumentsAgreements + fileName;
        try (FileOutputStream fos = new FileOutputStream(fullPath)){
            agreementDocs.write(fos);
            orderPickupDTO.setAgreementUrl(webUrl);//absolute path ?
        }
        agreementDocs.close();
    }
    public Map<String, String> extractFieldsData(
            OrganizationDetailsProjection organizationDetailsProjection, ClientOrder order,  OrderPickupDTO orderPickupDTO) {
        Map<String, String> fields = new HashMap<>();
        fields.put("brandname", organizationDetailsProjection.getBrandName());
        fields.put("phonenumber", organizationDetailsProjection.getPhoneNumber());
        fields.put("emailaddress", organizationDetailsProjection.getEmailAddress());
        fields.put("companyname", order.getOrganization().getName());
        fields.put("serverurl", serverDataService.getServerFullAddress());
        fields.put("legaladdress", organizationDetailsProjection.getLegalAddress());
        fields.put("ogrn", organizationDetailsProjection.getOgrn());
        fields.put("inn", organizationDetailsProjection.getInn());
        fields.put("kpp", organizationDetailsProjection.getKpp());
        fields.put("currentaccount", organizationDetailsProjection.getCurrentAccount());
        fields.put("bank", organizationDetailsProjection.getBankName());
        fields.put("bic", organizationDetailsProjection.getBic());
        fields.put("correspondaccount", organizationDetailsProjection.getCorrespondAccount());
        fields.put("sname", order.getClient().getSecondName());
        fields.put("name", order.getClient().getName());
        fields.put("patronymic", order.getClient().getPatronymic());
        fields.put("clientaddress", order.getAddress());
        fields.put("clientphonenumber", order.getClient().getPhone());
        fields.put("itemscount", String.valueOf(orderPickupDTO.getItemsCount()));
        fields.put("couriercomment", orderPickupDTO.getComment());
        //more...
        return fields;
    }
}
