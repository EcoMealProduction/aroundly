package com.backend.domain;

import com.backend.domain.reactions.EngagementStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EngagementStatsTest {

  private EngagementStats engagementStats;

  @BeforeEach
  void setup() {
    engagementStats = EngagementStats.builder()
        .build();
  }

  @Test
  void testAddConfirm() {
    EngagementStats confirmedHappening = engagementStats.addConfirm();
    assert confirmedHappening.confirms() == 1;
  }

  @Test
  void testAddDeny() {
    EngagementStats deniedHappening = engagementStats.addDeny();
    assert deniedHappening.denies() == 1;
    assert  deniedHappening.consecutiveDenies() == 1;

  }

  @Test
  void testRemoveConsecutiveDenies() {
    EngagementStats deniedHappening = engagementStats.addDeny();
    EngagementStats anotherDeniedHappening = deniedHappening.addDeny();
    assert  anotherDeniedHappening.consecutiveDenies() == 2;

    EngagementStats confirmedHappening = anotherDeniedHappening.addConfirm();
    assert  confirmedHappening.consecutiveDenies() == 0;
  }

}
