# myvotingsystem
Une application de vote par ranking, Spring Boot / Angular

# Presentation

This application lets you organize elections, vote, and consult their results. The voters must rank all the candidates by order of preference.

To calculate the winner of the election, the voting system used is the Schulze method. This method garantees to elect the Condorcet winner : the candidate that wins a majority of the vote in every head-to-head election against each of the other candidates, that is, a candidate preferred by more voters than any others, whenever there is such a candidate.

If there is no Condorcet winner, the Schulze method will elect the best candidate from the top candidates. Several heuristics were invented to achieve this. Here we use the Schwartz method. It is still possible to end up with a tie, if the number of voters is low.

For more details, see the wikipedia page : https://en.wikipedia.org/wiki/Schulze_method

# Demonstration

A demonstration is hosted on Heroku (there is one app for the backend, and another one for the frontend).
If the application has not been used for some time, the two Heroku apps are sleeping and you will have to wait a few seconds until they restart.

http://thibault-voting-app-front.herokuapp.com



