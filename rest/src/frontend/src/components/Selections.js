import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormHelperText from '@mui/material/FormHelperText';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

export default function Selections() {
    const [city, setCity] = React.useState('');
    const [vehicle, setVehicle] = React.useState('');

    const handleChangeForCity = (event: SelectChangeEvent) => {
        setCity(event.target.value);
    };
    const handleChangeForVehicle = (event: SelectChangeEvent) => {
        setVehicle(event.target.value);
    };

    return (
        <div>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
                <InputLabel>City</InputLabel>
                <Select
                    value={city}
                    label="City"
                    onChange={handleChangeForCity}
                >
                    <MenuItem value="">
                    </MenuItem>
                    <MenuItem value="Tallinn">Tallinn</MenuItem>
                    <MenuItem value="Tartu">Tartu</MenuItem>
                    <MenuItem value="Pärnu">Pärnu</MenuItem>
                </Select>
                <FormHelperText>Choose city for delivery</FormHelperText>
            </FormControl>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
                <InputLabel>Vehicle</InputLabel>
                <Select
                    value={vehicle}
                    label="Vehicle"
                    onChange={handleChangeForVehicle}
                >
                    <MenuItem value="">
                    </MenuItem>
                    <MenuItem value="Car">Car</MenuItem>
                    <MenuItem value="Scooter">Scooter</MenuItem>
                    <MenuItem value="Bike">Bike</MenuItem>
                </Select>
                <FormHelperText>Choose vehicle type for delivery</FormHelperText>
            </FormControl>
        </div>
    );
}