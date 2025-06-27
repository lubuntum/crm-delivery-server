package com.delivery.mydelivery.tgbot;

import com.delivery.mydelivery.config.EnvPropertiesConfig;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class TelegramBotService {

    private final Environment environment;

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.telegram.org/bot%s/getUpdates?offset=%d";
    public static final String API_NOTIFICATION_URL = "https://api.telegram.org/bot%s/sendMessage";
    private final EmployeeService employeeService;

    public TelegramBotService(
            Environment environment,
            RestTemplate restTemplate,
            EmployeeService employeeService) {
        this.environment = environment;
        this.restTemplate = restTemplate;
        this.employeeService = employeeService;
    }
    public void checkEmployeesRequests() {
        List<Update> updates = getUpdates(0);
        updates.forEach(update -> {
            try {
                Employee employee = employeeService.getEmployeeByPhone(update.getMessage().getText());
                if (employee == null || employee.getChatId() != null) return;
                employee.setChatId(update.getMessage().getChatId().toString());
                employeeService.save(employee);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }
    public List<Update> getUpdates(int offset) {
        String url = String.format(API_URL, environment.getProperty(EnvPropertiesConfig.TG_BOT_TOKEN), offset);
        UpdatesResponse response = restTemplate.getForObject(url, UpdatesResponse.class);
        return response.getResult();
    }
    public void sendNotifications(Organization organization, ClientOrderDTO orderDTO) {
        String url = String.format(API_NOTIFICATION_URL, environment.getProperty(EnvPropertiesConfig.TG_BOT_TOKEN));
        String payload = orderDTO.toString();
        organization.getEmployees().forEach(e -> {
            if (e.getChatId() == null) return;
            String employeeRequestUrl = String.format("%s?chat_id=%s&text=%s",url, e.getChatId(), payload);
            restTemplate.getForObject(employeeRequestUrl, String.class);
        });
    }
}
