{
	<#if start?? && (start gt 0) >
	"from": "${start}",
	</#if>
	<#if pageSize?? && (pageSize gt 0) >
	"size": "${pageSize}",
	<#else>
	"size": 500,
	</#if>
	"query": {
		"bool": {
			"must": [
				<#if keyWord?? >
					{
						"multi_match": {
							"query": "${keyWord}",
							"fields": [
								"name"
							]
						}
					},
				</#if>
				<#if age?? >
					{
						"term": {
							"age": ${age}
						}
					},
				</#if>
				<#if name?? >
					{
						"term": {
							"name": ${name}
						}
					},
				</#if>
					{
						"constant_score": {
							"filter": {
								"range": {
									"age": {
										"gte": 0,
										"lte": 3
									}
								}
							}
						}
					}
			]
		}
	},
	"sort": [
		"_score",
		{"age":"asc"}
	]
}