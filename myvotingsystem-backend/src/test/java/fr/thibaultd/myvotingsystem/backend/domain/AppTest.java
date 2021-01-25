package fr.thibaultd.myvotingsystem.backend.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTest {

  @Test
  public void test_3Candidates_1CondorcetWinner() {
    // GIVEN
    ElectionEntity election = new ElectionEntity("election test", LocalDate.now());
    CandidateEntity candidate1 = new CandidateEntity("cand 1", election);
    CandidateEntity candidate2 = new CandidateEntity("cand 2", election);
    CandidateEntity candidate3 = new CandidateEntity("cand 3", election);
    VoteEntity vote1 = new VoteEntity(election, Arrays.asList(candidate1, candidate2, candidate3));
    VoteEntity vote2 = new VoteEntity(election, Arrays.asList(candidate2, candidate1, candidate3));
    VoteEntity vote3 = new VoteEntity(election, Arrays.asList(candidate3, candidate1, candidate2));
    List<VoteEntity> votes = Arrays.asList(vote1, vote2, vote3);

    // WHEN
    VoteResults results = new VoteResults(election, votes);
    Set<CandidateEntity> winners = results.getWinners();

    // THEN
    assertEquals(1, winners.size());
    assertTrue(winners.contains(candidate1));
  }

  @Test
  public void test_3Candidates_3SchulzeWinners() {
    // GIVEN
    ElectionEntity election = new ElectionEntity("election test", LocalDate.now());
    CandidateEntity candidate1 = new CandidateEntity("cand 1", election);
    CandidateEntity candidate2 = new CandidateEntity("cand 2", election);
    CandidateEntity candidate3 = new CandidateEntity("cand 3", election);
    VoteEntity vote1 = new VoteEntity(election, Arrays.asList(candidate1, candidate2, candidate3));
    VoteEntity vote2 = new VoteEntity(election, Arrays.asList(candidate3, candidate1, candidate2));
    VoteEntity vote3 = new VoteEntity(election, Arrays.asList(candidate2, candidate3, candidate1));
    List<VoteEntity> votes = Arrays.asList(vote1, vote2, vote3);

    // WHEN
    VoteResults results = new VoteResults(election, votes);
    Set<CandidateEntity> winners = results.getWinners();

    // THEN
    assertEquals(3, winners.size());
  }

  @Test
  public void test_4Candidates_3SchulzeWinners() {
    // GIVEN
    ElectionEntity election = new ElectionEntity("election test", LocalDate.now());
    CandidateEntity candidate1 = new CandidateEntity("cand 1", election);
    CandidateEntity candidate2 = new CandidateEntity("cand 2", election);
    CandidateEntity candidate3 = new CandidateEntity("cand 3", election);
    CandidateEntity candidate4 = new CandidateEntity("cand 4", election);
    VoteEntity vote1 =
        new VoteEntity(election, Arrays.asList(candidate1, candidate2, candidate3, candidate4));
    VoteEntity vote2 =
        new VoteEntity(election, Arrays.asList(candidate3, candidate1, candidate2, candidate4));
    VoteEntity vote3 =
        new VoteEntity(election, Arrays.asList(candidate2, candidate3, candidate1, candidate4));
    List<VoteEntity> votes = Arrays.asList(vote1, vote2, vote3);

    // WHEN
    VoteResults results = new VoteResults(election, votes);
    Set<CandidateEntity> winners = results.getWinners();

    // THEN
    assertEquals(3, winners.size());
    assertTrue(winners.contains(candidate1));
    assertTrue(winners.contains(candidate2));
    assertTrue(winners.contains(candidate3));
  }

  @Test
  public void test_3Candidates_2SchulzeWinners() {
    // GIVEN
    ElectionEntity election = new ElectionEntity("election test", LocalDate.now());
    CandidateEntity candidate1 = new CandidateEntity("cand 1", election);
    CandidateEntity candidate2 = new CandidateEntity("cand 2", election);
    CandidateEntity candidate3 = new CandidateEntity("cand 3", election);
    VoteEntity vote1 = new VoteEntity(election, Arrays.asList(candidate1, candidate2, candidate3));
    VoteEntity vote2 = new VoteEntity(election, Arrays.asList(candidate1, candidate2, candidate3));
    VoteEntity vote3 = new VoteEntity(election, Arrays.asList(candidate1, candidate2, candidate3));
    VoteEntity vote4 = new VoteEntity(election, Arrays.asList(candidate3, candidate1, candidate2));
    VoteEntity vote5 = new VoteEntity(election, Arrays.asList(candidate3, candidate1, candidate2));
    VoteEntity vote6 = new VoteEntity(election, Arrays.asList(candidate3, candidate1, candidate2));
    VoteEntity vote7 = new VoteEntity(election, Arrays.asList(candidate2, candidate3, candidate1));
    List<VoteEntity> votes = Arrays.asList(vote1, vote2, vote3, vote4, vote5, vote6, vote7);

    // WHEN
    VoteResults results = new VoteResults(election, votes);
    Set<CandidateEntity> winners = results.getWinners();

    // THEN
    assertEquals(2, winners.size());
    assertTrue(winners.contains(candidate1));
    assertTrue(winners.contains(candidate3));
  }
}
