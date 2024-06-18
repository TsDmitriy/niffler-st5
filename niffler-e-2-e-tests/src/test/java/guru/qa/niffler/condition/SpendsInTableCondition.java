package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

public class SpendsInTableCondition extends WebElementsCondition {

    private final SpendJson[] expectedSpends;

    public SpendsInTableCondition(SpendJson[] expectedSpends) {
        this.expectedSpends = expectedSpends;
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, List<WebElement> elements) {
        if (elements.size() != expectedSpends.length) {
            return CheckResult.rejected(
                    "Spending table size mismatch",
                    elements.size()
            );
        }

        for (int i = 0; i < elements.size(); i++) {
            final String DATE_FORMATTER = "dd MMM yy";

            WebElement row = elements.get(i);
            SpendJson expectedSpendForRow = expectedSpends[i];

            List<WebElement> td = row.findElements(By.cssSelector("td"));

            String date = td.get(1).getText();
            boolean dateResult = date.contains(
                    DateUtils.formatCurrentDate(
                            expectedSpendForRow.spendDate(),
                            DATE_FORMATTER
                    )
            );

            if (!dateResult) {
                return CheckResult.rejected(
                        "Spends table: date mismatch",
                        date
                );
            }

            String amount = new BigDecimal(td.get(2).getText()).toString();
            boolean amountResult = amount.equals(
                    expectedSpendForRow.amount().toString()
            );

            if (!amountResult) {
                return CheckResult.rejected(
                        "Spends table: amount mismatch",
                        amount
                );
            }

            String currency = td.get(3).getText().toString();
            boolean currencyResult = currency.contains(
                    String.valueOf(expectedSpendForRow.currency())
            );

            if (!currencyResult) {
                return CheckResult.rejected(
                        "Spends table: name currency",
                        currency
                );
            }
            String category = td.get(4).getText().toString();
            boolean categoryResult = category.contains(
                    String.valueOf(expectedSpendForRow.category())
            );

            if (!categoryResult) {
                return CheckResult.rejected(
                        "Spends table: category mismatch",
                        category
                );
            }

            String description = td.get(5).getText().toString();
            boolean descriptionResult = description.contains(
                    String.valueOf(expectedSpendForRow.description())
            );

            if (!descriptionResult) {
                return CheckResult.rejected(
                        "Spends table: description mismatch",
                        description
                );
            }
        }
        return CheckResult.accepted();
    }

    @Override
    public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        StringBuilder expectedSpend = new StringBuilder();
        List<WebElement> elements = collection.getElements();

        StringBuilder spends = new StringBuilder();
        for (int j = 0; j < elements.size(); j++) {
            WebElement row = elements.get(j);
            List<WebElement> td = row.findElements(By.cssSelector("td"));
            spends.append(String.format("%s | %s | %s | %s | %s \n",
                    td.get(1).getText(),
                    new BigDecimal(td.get(2).getText()),
                    td.get(3).getText(),
                    td.get(4).getText(),
                    td.get(5).getText()
            ));
        }

        for (int i = 0; i < expectedSpends.length; i++) {
            SpendJson expectedSpendForRow = expectedSpends[i];

            expectedSpend.append(String.format("%s | %s | %s | %s | %s \n",
                    expectedSpendForRow.spendDate(),
                    expectedSpendForRow.amount().toString(),
                    expectedSpendForRow.currency().toString(),
                    expectedSpendForRow.category(),
                    expectedSpendForRow.description()
            ));
        }

        String message = lastCheckResult.getMessageOrElse(() -> "Spending mismatch");
        throw new SpendMismatchException(message,
                collection,
                expectedSpend.toString(),
                spends.toString(),
                explanation,
                timeoutMs,
                cause);
    }

    @Override
    public String toString() {
        return "";
    }
}
