package com.sicredi.test.repository;

import com.sicredi.test.model.ResultDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Repository
public class VotingResultsRepository {

  private final ReactiveMongoTemplate mongoTemplate;

  public Flux<ResultDto> getResult(String agendaId) {

    List<AggregationOperation> aggregationOperations = new ArrayList<>();

    if (StringUtils.hasText(agendaId)) {
      aggregationOperations.add(Aggregation.match(Criteria.where("_id").is(agendaId)));
    } else {
      aggregationOperations.add(Aggregation.match(Criteria.where("_id").exists(true)));
    }

    LookupOperation lookupVotingSessionOperation = LookupOperation.newLookup()
        .from("votingSession")
        .localField("_id")
        .foreignField("agendaId")
        .as("votingSessions");

    aggregationOperations.add(lookupVotingSessionOperation);

    Aggregation aggregate = Aggregation.newAggregation(aggregationOperations);
    return mongoTemplate.aggregate(aggregate, "agenda", ResultDto.class);
  }
}
