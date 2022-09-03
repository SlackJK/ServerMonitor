import pandas as pd
from dash import Dash, html, dcc
from dash.dependencies import Input, Output
import plotly.express as px

df = pd.read_csv(r"test_cpu_data.csv")
fig = px.line(df, x="time", y="cpu_temp")


app = Dash(__name__)
app.layout = html.Div(children=[
    html.H1(children="Hello world"),
    dcc.Graph(
        id="my_graph",
        figure=fig
    ),
    dcc.Interval(
        id="interval_component",
        interval=1*1000,  # miliseconds
        n_intervals=0
    )
])


@app.callback(Output("my_graph", "figure"),
              Input("interval_component", "n_intervals"))
def update_graph(n):
    df = pd.read_csv(r"test_cpu_data.csv")
    fig = px.line(df, x="time", y="cpu_temp")
    return fig


if __name__ == '__main__':
    app.run_server(debug=True)
