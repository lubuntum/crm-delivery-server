package com.delivery.mydelivery.services.documents.autofill.services;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.productmanage.Item;
import com.delivery.mydelivery.database.projections.organization.OrganizationDetailsProjection;
import com.delivery.mydelivery.database.services.ordermanage.OrderFinishService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.database.services.organization.OrganizationDetailsService;
import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import com.delivery.mydelivery.services.documents.autofill.DocxFillerUtil;
import com.delivery.mydelivery.services.documents.autofill.TableDocxFillerUtil;
import com.delivery.mydelivery.utility.file.FIleUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompletionWorkFillerService {
    private final TableDocxFillerUtil tableDocxFillerUtil;
    private final OrderService orderService;
    private final OrderFinishService orderFinishService;
    private final OrganizationDetailsService organizationDetailsService;
    @Value("${documents.completions}")
    private String documentsPath;
    @Value("${web.documents.completions}")
    private String webDocumentsPath;
    @Value("${documents.templates.completion}")
    private String completionTemplate;

    public void createCompletionWork(OrderFinishDTO orderFinishDTO) throws IOException {
        ClientOrder order = orderService.getClientOrderById(orderFinishDTO.getOrderId());
        OrganizationDetailsProjection orgDetails = organizationDetailsService.getOrganizationDetailsByOrganizationId(order.getOrganization().getId());
        if (orgDetails == null) return;
        Map<String, String> fields = extractFieldsData(orderFinishDTO, order, orgDetails);
        XWPFDocument completionDocs = tableDocxFillerUtil.fillDocxTable(
                order,
                DocxFillerUtil.fillDocx(completionTemplate, fields),
                0);
        String fileName = FIleUtil.generateUniqueFilename("completion.docx");
        String fullPath = documentsPath + fileName;
        String webUrl = webDocumentsPath + fileName;
        try (FileOutputStream fos = new FileOutputStream(fullPath)){
            completionDocs.write(fos);
            orderFinishDTO.setCompletionUrl(webUrl);
            //save paths
        }
        completionDocs.close();
        //fill docx and etc
    }
    private Map<String, String> extractFieldsData(OrderFinishDTO orderFinishDTO,
                                                 ClientOrder clientOrder,
                                                 OrganizationDetailsProjection orgDetails){
        BigDecimal itemsTotal = clientOrder.getItems().stream()
                .map(Item::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal delivery = Optional.ofNullable(orderFinishDTO.getDeliveryPrice()).orElse(BigDecimal.ZERO);
        BigDecimal tips = Optional.ofNullable(orderFinishDTO.getTips()).orElse(BigDecimal.ZERO);
        BigDecimal totalPayment = itemsTotal.add(delivery).add(tips);

        Map<String, String> fields = new HashMap<>();
        fields.put("ordernumber", clientOrder.getSerialNumber());
        fields.put("itemstotalprice", itemsTotal.toString());
        fields.put("delivery", delivery.toString());
        fields.put("tips", tips.toString());
        fields.put("totalpayment", totalPayment.toString());
        fields.put("clientfullname", String.format("%s %s %s",
                clientOrder.getClient().getSecondName(),
                clientOrder.getClient().getName(),
                clientOrder.getClient().getPatronymic()));
        fields.put("clientaddress", clientOrder.getAddress());
        fields.put("clientphone", clientOrder.getClient().getPhone());
        fields.put("companyname", clientOrder.getOrganization().getName());
        fields.put("companynumber", orgDetails.getPhoneNumber());
        fields.put("companyaddress", orgDetails.getLegalAddress());
        fields.put("ogrn", orgDetails.getOgrn());
        fields.put("inn", orgDetails.getInn());
        fields.put("kpp", orgDetails.getKpp());
        fields.put("currentaccount", orgDetails.getCurrentAccount());
        fields.put("bank", orgDetails.getBankName());
        fields.put("bic", orgDetails.getBic());
        fields.put("correspondaccount", orgDetails.getCorrespondAccount());
        return fields;
    }
}
