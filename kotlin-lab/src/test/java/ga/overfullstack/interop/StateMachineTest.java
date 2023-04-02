package ga.overfullstack.interop;

import com.tinder.StateMachine;
import ga.overfullstack.interop.State.State.Solid;
import kotlin.Unit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StateMachineTest {

  @Test
  @DisplayName("State Machine")
  void stateMachine() {
    final var sm = StateMachine.Companion.create(
        gb -> {
          gb.initialState(Solid.INSTANCE);
          return Unit.INSTANCE;
        });
    Assertions.assertThat(sm).isNotNull();
  }
}
