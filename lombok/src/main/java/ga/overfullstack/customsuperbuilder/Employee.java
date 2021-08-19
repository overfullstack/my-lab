package ga.overfullstack.customsuperbuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import ga.overfullstack.custombuilder.Message.MessageBuilder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents an employee in the company.
 */
@Getter
@SuperBuilder
public abstract class Employee<IDT> {

  /**
   * Id of the employee
   */
  private final IDT id;

  private List<?> files;

  /**
   * Employees name
   */
  private final String name;

  /**
   * Date when employee joined the company
   */
  private final LocalDate joiningDate;

  private final Map<?, ?> promotionDates;

  public static abstract class EmployeeBuilder<IDT, C extends Employee<IDT>, B extends EmployeeBuilder<IDT, C, B>> {
    public B id(IDT id) {
      this.id = id;
      return self();
    }

    public <FileT> B files(List<FileT> files) {
      this.files = files;
      return self();
    }
  }
}
