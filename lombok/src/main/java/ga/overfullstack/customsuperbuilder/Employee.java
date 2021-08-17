package ga.overfullstack.customsuperbuilder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Map;

/**
 * Represents an employee in the company.
 */
@Getter
@SuperBuilder
public class Employee<IDT> {

  /**
   * Id of the employee
   */
  private final IDT id;

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

    private IDT id;

    public B id(IDT id) {
      this.id = id;
      return self();
    }
    
  }

  public static <IDT> EmployeeBuilder<IDT, ?, ?> builder() {
    return (EmployeeBuilder<IDT, ?, ?>) new EmployeeBuilderImpl<IDT>();
  }

}
